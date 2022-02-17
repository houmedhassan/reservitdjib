package com.safara.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
	
	/*@Autowired
	ServletContext context;
 	*/
  Logger log = LoggerFactory.getLogger(this.getClass().getName());
  private final Path rootLocation = Paths.get("../images");
 
  public String  store(MultipartFile file, String name ) {
    try {      
    	//this.rootLocation.
    	System.out.println(rootLocation.getRoot());
    
    	Files.copy(file.getInputStream(), this.rootLocation.resolve(name+"_"+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
    	return file.getOriginalFilename();
    } catch (Exception e) {
     
    	e.printStackTrace();
    	 throw new RuntimeException("ECHEC!");
    }
  }
 
  public Resource loadFile(String filename) {
    try {
      Path file = rootLocation.resolve(filename);
      System.out.println(file.getFileName());
      System.out.println(file.toUri());
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      }else if(!resource.exists()) 
      {
    	  throw new RuntimeException("ECHEC! : FILE N'EXISTE PAS "); 
      } else {
        throw new RuntimeException("ECHEC!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("ECHEC!");
    }
  }
 
  /**
   * initisalisation de dossier de stockage
   */
  public void init() {
    try {
    	if(Files.exists(rootLocation))
    	{
    		
    	}else {
    		Files.createDirectory(rootLocation);
    	}
      Files.createDirectory(rootLocation);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize storage!");
    }
  }
  
  /**
   * autre methode pour charger un fichier
   * 
   * @param is
   * @param path
   */
  public void saveImage(InputStream is, String path)
  {
	  try {
		  OutputStream os = new FileOutputStream(new File(path));
		  int read =0;
		  byte[] bytes = new byte[10254];
		  while((read = is.read(bytes)) != -1)
		  {
			  os.write(bytes, 0, read);
		  }
		  
		  os.flush();
		  os.close();
		  
	  }catch(IOException ex)
	  {
		  
	  }
  }
}