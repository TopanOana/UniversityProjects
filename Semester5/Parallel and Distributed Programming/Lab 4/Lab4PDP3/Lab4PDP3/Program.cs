using System.Net;
using System.Net.Sockets;
using System.Text;

namespace PPDLab4
{
    public static class Program
    {
        public static void Main()
        {
            var entry = Dns.GetHostEntry(State.Host);
            var socket = new Socket(SocketType.Stream, ProtocolType.Tcp);
            var endpoint = new IPEndPoint(entry.AddressList[0], State.Port);
            var state = new State(socket);
            //state.Socket.BeginConnect(endpoint, ConnectCallback, state);
            //Task<State> future = BeginConnect(endpoint, state);
            Task<State> result = BeginConnect(endpoint, state);
            var requestText = $"GET /documente-utile/ HTTP/1.1\r\nHost: {State.Host}\r\n\r\n";
            //var requestBytes = Encoding.UTF8.GetBytes(requestText);
            //future.ContinueWith((Task<State> f) => SendCall(state, requestText));
            Task<State> res = SendCall(result.Result, requestText);
            state.ReceiveDone.WaitOne();
            state.Socket.Close();
        }

        private static Task<State> BeginConnect(IPEndPoint endpoint, State state)
        {
            TaskCompletionSource<State> result = new TaskCompletionSource<State>();
            state.Socket.BeginConnect(endpoint, (IAsyncResult ar) =>
            {
                state.Socket.EndConnect(ar);
                state.ConnectDone.Set();
                result.SetResult(state);
            }, state);
            return result.Task;
        }

        private static async Task<State> SendCall(State state, String requestText)
        {
            var requestBytes = Encoding.UTF8.GetBytes(requestText);
            TaskCompletionSource<State> taskCompletionSource = new TaskCompletionSource<State>();
            state.Socket.BeginSend(requestBytes, 0, requestBytes.Length, SocketFlags.None, (IAsyncResult ar) =>
            {
                var bytesSent = state.Socket.EndSend(ar);
                state.SendDone.Set();
                taskCompletionSource.SetResult(state);
            }, state);
            State result = await BeginReceive(state);
            return result;
        }

        private static async Task<State> BeginReceive(State state)
        {
            State result = state;
            state.Socket.BeginReceive(state.Buffer, 0, State.BufferLength, SocketFlags.None, async (IAsyncResult ar) =>
            {
                var bytesReceived = state.Socket.EndReceive(ar);
                if (bytesReceived == 0)
                {
                    Console.WriteLine(state.Content.ToString());
                    state.ReceiveDone.Set();
                }
                else
                {
                    var responseText = Encoding.UTF8.GetString(state.Buffer, 0, bytesReceived);
                    state.Content.Append(responseText);
                    result = await BeginReceive(state);
                }
            }, state);
            return result;
        }

     

        public sealed class State
        {
            public const string Host = "www.cnatdcu.ro";
            public const int Port = 80;
            public const int BufferLength = 1024;
            public readonly byte[] Buffer = new byte[BufferLength];
            public readonly ManualResetEvent ConnectDone = new ManualResetEvent(false);
            public readonly StringBuilder Content = new StringBuilder();
            public readonly ManualResetEvent ReceiveDone = new ManualResetEvent(false);
            public readonly ManualResetEvent SendDone = new ManualResetEvent(false);
            public readonly Socket Socket;

            public State(Socket socket)
            {
                Socket = socket;
            }
        }
    }
}