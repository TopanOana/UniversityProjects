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

namespace DBMSLab1
{
    public partial class Form1 : Form
    {
        //connection to database
        SqlConnection sqlConnection;
        //data adapters for tables
        //the bridge between the dataset and the sql server
        //used to obtain data and save the changes back in the database
        SqlDataAdapter brandsDataAdapter;
        SqlDataAdapter productsDataAdapter;
        //object that contains the tables 
        //temporary storage for use in application
        //local in-memory cache
        DataSet dataSet;

        //binds the data to the application
        //what you see in the application
        BindingSource brandsBindingSource;
        BindingSource productsBindingSource;

        SqlCommandBuilder sqlCommandBuilder;

        string brandsQuery;
        string productsQuery;

        public Form1()
        {
            InitializeComponent();
            fillData();
        }

        void fillData()
        {
            //SQLConnection initialization
            sqlConnection = new SqlConnection(getConnectionString());

            //queries for the tables
            productsQuery = "SELECT * FROM PRODUCTS";
            brandsQuery = "SELECT * FROM BRANDS";

            //DataAdapters
            productsDataAdapter = new SqlDataAdapter(productsQuery,sqlConnection);
            brandsDataAdapter = new SqlDataAdapter(brandsQuery,sqlConnection);

            //DataSet
            dataSet = new DataSet();

            //Fill the data adapters with the data from the dataset
            productsDataAdapter.Fill(dataSet, "Products");
            brandsDataAdapter.Fill(dataSet, "Brands");


            sqlCommandBuilder = new SqlCommandBuilder(productsDataAdapter);

            //add the parent-child relation between brands and products
            //add it on the dataset
            dataSet.Relations.Add("BrandsProduct",
                dataSet.Tables["Brands"].Columns["id"],
                dataSet.Tables["Products"].Columns["brand_id"]);


            //binding sources to the data set
            brandsBindingSource = new BindingSource();
            brandsBindingSource.DataSource = dataSet.Tables["Brands"];
            productsBindingSource = new BindingSource(brandsBindingSource, "BrandsProduct");


            ///dataGridViews
            //populate the grid views that you can see in the application
            //using the binding sources
            dataGridViewBrands.DataSource = brandsBindingSource; 
            dataGridViewProducts.DataSource = productsBindingSource;

            ///sqlCommandBuilder
            sqlCommandBuilder.GetUpdateCommand();
        }


        string getConnectionString()
        {
            return "Data Source=DESKTOP-S2N055K; Initial Catalog = SkateShop;"+"Integrated Security=true;";
        }

        private void updateButton_Click(object sender, EventArgs e)
        {
            //the dataAdapter checks whether there have been changes made on the database
            //if so, the respective insert,update or delete commands are executed on the database
            try{
                productsDataAdapter.Update(dataSet, "Products");
            }catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
