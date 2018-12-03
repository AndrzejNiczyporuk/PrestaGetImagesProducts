/*
 * www.zydor.pl
 *
 *
 *
 */
package pswebservice.examples;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.*;
import pswebservice.PSWebServiceClient;
import pswebservice.PrestaShopWebserviceException;

import static javax.swing.UIManager.get;


/**
 *
 * @author zydor
 */
public class PSWebServiceExample {

    /**
     * @param args the command line arguments
     * @throws pswebservice.PrestaShopWebserviceException
     * @throws javax.xml.transform.TransformerConfigurationException
     */  
    public static void main(String[] args) throws PrestaShopWebserviceException, IOException {
        // Get list of products
        ArrayList<String> strUriProduct = new ArrayList<>();

        PSWebServiceClient wsClient = new PSWebServiceClient("http://presta.example.com", "TDMFCYSN88847HMMPA36EVZAYMK9MN9A", "C:\\Users\\account\\", false);
         try {
           HashMap<String, Object> opt = new HashMap();
            opt.put("resource", "products");
            Document xml = wsClient.get(opt);
            NodeList nList = xml.getElementsByTagName("product");

           //save ID of products
            for (int temp = 0; temp < nList.getLength(); temp++) {
                //strUriProduct.add(nList.item(temp).getAttributes().getNamedItem("xlink:href").getNodeValue());
                strUriProduct.add(nList.item(temp).getAttributes().getNamedItem("id").getNodeValue());
            }
        }
      catch (PrestaShopWebserviceException  exception)
      {
       System.out.println(exception);
      }

      //Get immageurl for imaage of products
      ArrayList<String> strUriProductImage = new ArrayList<>();
      for (String s : strUriProduct) {
          System.out.println(s);
          HashMap<String, Object> opt = new HashMap();
          opt.put("resource", "products");
          opt.put("id", s);
          Document xml = wsClient.get(opt);
          NodeList nList = xml.getElementsByTagName("image");

          for (int temp = 0; temp < nList.getLength(); temp++) {
               strUriProductImage.add(nList.item(temp).getAttributes().getNamedItem("xlink:href").getNodeValue());
               System.out.println(strUriProductImage.get(strUriProductImage.size()-1));
           }
      }
      //Write String URL
        System.out.println("Save All");
        for (String s : strUriProductImage) {

            wsClient.getImg(s);
            System.out.println("Get from  " + s);
        }
    }
    
}


class PSWebServiceWrapper {

    private final String shopUrl = "http://presta.example.com";
    private final String key = "TDMFCYSN88847HMMPA36EVZAYMK9MN9A";
    private final String path = "C:\\Users\\account\\";
    private final boolean debug = false;
    private PSWebServiceClient ws;
    
    public PSWebServiceWrapper()  {
        this.ws = new PSWebServiceClient(shopUrl,key, path, debug);
    }



    public void addProductExample(String nameProduct ) throws PrestaShopWebserviceException, TransformerException, IOException{
        
        HashMap<String,Object> getSchemaOpt = new HashMap();
        getSchemaOpt.put("url",shopUrl+"/api/products?schema=blank");       
        Document schema = ws.get(getSchemaOpt);     
        
        schema.getElementsByTagName("id_category_default").item(0).setTextContent("2");  
        schema.getElementsByTagName("price").item(0).setTextContent("100"); 
        schema.getElementsByTagName("active").item(0).setTextContent("1"); 
        schema.getElementsByTagName("available_for_order").item(0).setTextContent("1"); 
        schema.getElementsByTagName("show_price").item(0).setTextContent("1"); 
        schema.getElementsByTagName("indexed").item(0).setTextContent("1");
       
        Element category = schema.createElement("category");
        Element catId = schema.createElement("id");
        catId.setTextContent("2");
        category.appendChild(catId);
        schema.getElementsByTagName("categories").item(0).appendChild(category);
        
        Element name = (Element) schema.getElementsByTagName("name").item(0).getFirstChild();
        name.appendChild(schema.createCDATASection(nameProduct ));
        name.setAttribute("id", "2");
        name.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+2);
               
        Element description = (Element) schema.getElementsByTagName("description").item(0).getFirstChild();
        description.appendChild(schema.createCDATASection(nameProduct ));
        description.setAttribute("id", "2");
        description.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+2);
        
        Element description_short = (Element) schema.getElementsByTagName("description_short").item(0).getFirstChild();
        description_short.appendChild(schema.createCDATASection(nameProduct ));
        description_short.setAttribute("id", "2");
        description_short.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+2);
        
        Element link_rewrite = (Element) schema.getElementsByTagName("link_rewrite").item(0).getFirstChild();
        link_rewrite.appendChild(schema.createCDATASection(nameProduct));
        link_rewrite.setAttribute("id", "2");
        link_rewrite.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+2);
        
        Element meta_title = (Element) schema.getElementsByTagName("meta_title").item(0).getFirstChild();
        meta_title.appendChild(schema.createCDATASection(nameProduct));
        meta_title.setAttribute("id", "2");
        meta_title.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+2);
        
        Element meta_description = (Element) schema.getElementsByTagName("meta_description").item(0).getFirstChild();
        meta_description.appendChild(schema.createCDATASection(nameProduct));
        meta_description.setAttribute("id", "2");
        meta_description.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+2);
        
        Element meta_keywords = (Element) schema.getElementsByTagName("meta_keywords").item(0).getFirstChild();
        meta_keywords.appendChild(schema.createCDATASection(nameProduct));
        meta_keywords.setAttribute("id", "2");
        meta_keywords.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+2);
        
        Element available_now = (Element) schema.getElementsByTagName("available_now").item(0).getFirstChild();
        available_now.appendChild(schema.createCDATASection(nameProduct));
        available_now.setAttribute("id", "2");
        available_now.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+2);
        
        Element available_later = (Element) schema.getElementsByTagName("available_later").item(0).getFirstChild();
        available_later.appendChild(schema.createCDATASection("available later"));
        available_later.setAttribute("id", "2");
        available_later.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+2);
              
        System.out.println(ws.DocumentToString(schema));
        
	HashMap<String,Object> productOpt = new HashMap();
        productOpt.put("resource", "products");
        productOpt.put("postXml", ws.DocumentToString(schema));
        Document product = ws.add(productOpt);     
        
        System.out.println(ws.DocumentToString(product));
        
     //   Document productImg = ws.addImg("http://example/exampleImg.jpg", Integer.valueOf(getCharacterDataFromElement((Element) product.getElementsByTagName("id").item(0))));

     //   System.out.println(ws.DocumentToString(productImg));
        
    }
    
    public static String getCharacterDataFromElement(Element e) {

        NodeList list = e.getChildNodes();
        String data;

        for(int index = 0; index < list.getLength(); index++){
            if(list.item(index) instanceof CharacterData){
                CharacterData child = (CharacterData) list.item(index);
                data = child.getData();

                if(data != null && data.trim().length() > 0)
                    return child.getData();
            }
        }
        return "";
    }
}
