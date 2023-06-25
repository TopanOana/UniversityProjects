using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using System.Configuration;
using System.Data.Common;

namespace DBMSLab2
{
    public partial class Form1 : Form
    { 
        //connection to database
        SqlConnection sqlConnection;
        //data adapters for tables
        //the bridge between the dataset and the sql server
        //used to obtain data and save the changes back in the database
        SqlDataAdapter parentDataAdapter;
        SqlDataAdapter childDataAdapter;

        //object that contains the tables 
        //temporary storage for use in application
        //local in-memory cache
        DataSet dataSet;

        //binds the data to the application
        //what you see in the application
        BindingSource parentBindingSource;
        BindingSource childBindingSource;

        SqlCommandBuilder sqlCommandBuilder;

        string parentQuery;
        string childQuery;

        string parentTableName;
        string childTableName;


        public Form1()
        {
            InitializeComponent();
            fillData();
        }
        string getConnectionString()
        {
            return "Data Source=DESKTOP-S2N055K; Initial Catalog = SkateShop;" + "Integrated Security=true;";
        }

        void fillData()
        {
            //SQLConnection initialization
            sqlConnection = new SqlConnection(getConnectionString());

            parentTableName = ConfigurationManager.AppSettings["parent"];
            childTableName = ConfigurationManager.AppSettings["child"];

            //queries for the tables
            parentQuery = "SELECT * FROM " + parentTableName;
            childQuery = "SELECT * FROM " + childTableName;

            //DataAdapters
            parentDataAdapter = new SqlDataAdapter(parentQuery, sqlConnection);
            childDataAdapter = new SqlDataAdapter(childQuery, sqlConnection);

            //DataSet
            dataSet = new DataSet();

            //Fill the data adapters with the data from the dataset
            parentDataAdapter.Fill(dataSet, parentTableName);
            childDataAdapter.Fill(dataSet, childTableName);

            sqlCommandBuilder = new SqlCommandBuilder(childDataAdapter);

            //add the parent-child relation between brands and products
            //add it on the dataset
            dataSet.Relations.Add(parentTableName+ childTableName,
                dataSet.Tables[parentTableName].Columns[ConfigurationManager.AppSettings["parent_id"]],
                dataSet.Tables[childTableName].Columns[ConfigurationManager.AppSettings["child_id"]]);

            //binding sources to the data set
            parentBindingSource = new BindingSource();
            parentBindingSource.DataSource = dataSet.Tables[parentTableName];
            childBindingSource = new BindingSource(parentBindingSource, parentTableName + childTableName);

            ///dataGridViews
            //populate the grid views that you can see in the application
            //using the binding sources
            parentGridView.DataSource = parentBindingSource;
            childGridView.DataSource = childBindingSource;

            parentLabel.Text = parentTableName;
            childLabel.Text = childTableName;

            ///sqlCommandBuilder
            sqlCommandBuilder.GetUpdateCommand();

        }

        private void updateButton_Click(object sender, EventArgs e)
        {
            // the dataAdapter checks whether there have been changes made on the database
            //if so, the respective insert,update or delete commands are executed on the database
            try
            {
                childDataAdapter.Update(dataSet, childTableName);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
