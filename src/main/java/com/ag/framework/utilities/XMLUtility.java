package com.ag.framework.utilities;

import com.ag.framework.core.DataManager;
import com.ag.framework.core.Global;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class XMLUtility {
    // public static Hashtable<Integer, Hashtable<String, String>> DataRow;
    public static String dir = System.getProperty("user.dir");
    static TransformerFactory transformerFactory = TransformerFactory
            .newInstance();
    static Transformer transformer;
    static String TestNGXMLPath = DataManager.getPropertyFile("TestNGXMLPath");

    static {
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }

    public XMLUtility() throws TransformerConfigurationException {
    }

    public static void createXml(Hashtable<Integer, Hashtable<String, String>> xmrow) throws Exception {

        try {
            // Initialization of drivers
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            //document type creation

            DOMImplementation domImpl = document.getImplementation();
            DocumentType doctype = domImpl.createDocumentType("suite", "http://testng.org", "http://testng.org/testng-1.0.dtd");
            //<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
            document.appendChild(doctype);


            // Element rootElementDocuType = document.createElement("root");


            // Get the number of parameter to be created in XML
            int totalnoofelementsflaggedtorun = xmrow.size();


            // Type the suite tag element in the XML file
            Element rootElementsuite = document.createElement("suite");
            //String createTextNode="<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\" >";
            //   DOMImplementation domImpl = document.getImplementation();
            // DocumentType documentType=domImpl.createDocumentType(createTextNode,"1");
            // Type the parameter set of lines in the XML file

            //for extent report listener
            Element rootElementListener = document.createElement("listeners");
            Element childelementListener1 = document.createElement("listener");
            Element childelementListener2 = document.createElement("listener");
            // Element childelementListener3 = document.createElement("listener");
            childelementListener1.setAttribute("class-name", "CustomListener");
            childelementListener2.setAttribute("class-name", "ITestListenerReporter");
            // childelementListener3.setAttribute("class-name", "CommonLib.Listeners.InvokedMethodListener");
            // Element rootElementlistenergroups = document.createElement("Listeners");
            // childelementListener.appendChild(rootElementlistenergroups);
            rootElementListener.appendChild(childelementListener1);
            rootElementListener.appendChild(childelementListener2);
            //rootElementListener.appendChild(childelementListener3);
            rootElementsuite.appendChild(rootElementListener);


/*

            try
            {
                List<String> RowsNo = Common.GetIterations(TestDataSheetRowNo, ",");


                for (int i=0; i<RowsNo.size(); i++)
                {
                    CurrentRowNo = RowsNo.get(i);
                    try {
                        TestData = GetExcelDataTable("Select * from " + SheetName + " where RowID=" + CurrentRowNo).get(1);

                        if (TestData != null)
                        {

                            Set<String> keySet = TestData.keySet();

                            // TestData.keySet().toArray(ArrayList<Executor>)
                            //String[] DataKey1 = DataKey.split(",");
                            for(String key: keySet)
                            {
                                Element rootElementparameter = document.createElement("parameter");

                                rootElementparameter.setAttribute("name", key);
                                String KD=key;
                                rootElementparameter.setAttribute("value", TestData.get(key));
                                rootElementsuite.appendChild(rootElementparameter);

                            }



                        }
                        else
                        {
                            System.out.println("Row does not exist");
                        }
                    }catch (Exception e)
                    {System.out.println(e.getMessage());}
                }
            } catch (Exception e){}
*/

         /*   for (int elementcounter = 1; elementcounter <=totalnoofelementsflaggedtorun; elementcounter++) {
                Element rootElementparameter = document.createElement("parameter");
                // String[] flagElement = DataRow.get(elementcounter).toString().split(";");
                String Fname=xmrow.get(1).get("Function_Name".toUpperCase());
               // Fname=DataRow.get(1).get
                String Flag=xmrow.get(1).get("Execution_Flag".toUpperCase());

                rootElementparameter.setAttribute("name",Fname );
                rootElementparameter.setAttribute("value", Flag);
                rootElementsuite.appendChild(rootElementparameter);
            }*/


            // Type the root elements in the XML file
            Element rootElementtest = document.createElement("test");
            Element rootElementClass = document.createElement("classes");
            Element childelementClass = document.createElement("class");


            // Assign attribute to the root elements
            childelementClass.setAttribute("name", "application.scenarios.TestScenarios");

            Element rootElementgroups = document.createElement("methods");

            // Assign attribute to the root elements
            rootElementsuite.setAttribute("name", Global.ProjectName);

            rootElementtest.setAttribute("name", Global.ProjectName);

            // Append values to the root elements
            //  rootElementDocuType.appendChild(rootElementsuite);
            rootElementsuite.appendChild(rootElementtest);
            rootElementtest.appendChild(rootElementClass);
            rootElementClass.appendChild(childelementClass);
            childelementClass.appendChild(rootElementgroups);
            // rootElementgroups.appendChild(rootElementrun);

            // document.appendChild(rootElementDocuType);
            document.appendChild(rootElementsuite);


            // Obtain the column value flag = "Y" in a loop
            for (int elementcounter = 1; elementcounter <= totalnoofelementsflaggedtorun; elementcounter++) {

                String element = "include";
                Element em = document.createElement(element);
                //String[] flagElement = DataRow.get(elementcounter).toString().split(";");
                //  String[] flagElement = lib.flaggedMethod.get(elementcounter).toString().split(";");
                //  em.setAttribute("name", flagElement[0]);
                String Fun = xmrow.get(elementcounter).get("Function_Name".toUpperCase());
                em.setAttribute("name", Fun);
                // em.setAttribute("invocationCount", "2");
                rootElementgroups.appendChild(em);

            }

            // Generate the file.
            FileWriter fstream = new FileWriter(dir + TestNGXMLPath);
            BufferedWriter out = new BufferedWriter(fstream);

            // Generate the required XML output file

            // transformer.setOutputProperty("DOCTYPE_SYSTEM", "testng-1.0.dtd");
            //  transformer.setOutputProperty("suite","http://testng.org/testng-1.0.dtd" );
            DOMSource source = new DOMSource(document);

            // Prints all the Generated Xml code in the File object
            StreamResult result = new StreamResult(fstream);
            transformer.transform(source, result);

            // close the generated file.
            out.close();
        } catch (DOMException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method runs the XML suite file dynamically
     **/

    public static void autoRunXml() {

        // Create a list
        List files = new ArrayList();

        // Add the required xml files to the list
        files.add(dir + TestNGXMLPath);

        // create object for TestNG
        org.testng.TestNG  tng = new TestNG();


        // Add the list of files to create a suite
        tng.setTestSuites(files);


        // Run the suite
        //String msg=tng.getDefaultTestName();
        tng.run();

        XmlSuite suite = new XmlSuite();
        suite.setName("TmpSuite");


    }

}
