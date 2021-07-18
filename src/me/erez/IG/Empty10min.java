package me.erez.IG;

import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
 
/**
 * This utility class implements a method that removes a non-empty directory
 * on a FTP server.
 * @author www.codejava.net
 */
public class Empty10min {
 
    /**
     * Removes a non-empty directory by delete all its sub files and
     * sub directories recursively. And finally remove the directory.
     */
	 public static void removeDirectory(Object ftpClient, String parentDir,
	            String currentDir) throws IOException {
		
	        String dirToList = parentDir;
	        if (!currentDir.equals("")) {
	            dirToList += "/" + currentDir;
	        }
	 
	        FTPFile[] subFiles = ((FTPClient) ftpClient).listFiles(dirToList);
	 
	        if (subFiles != null && subFiles.length > 0) {
	            for (FTPFile aFile : subFiles) {
	                String currentFileName = aFile.getName();
	                if (currentFileName.equals(".") || currentFileName.equals("..")) {
	                    // skip parent directory and the directory itself
	                    continue;
	                }
	                String filePath = parentDir + "/" + currentDir + "/"
	                        + currentFileName;
	                if (currentDir.equals("")) {
	                    filePath = parentDir + "/" + currentFileName;
	                }
	 
	                if (aFile.isDirectory()) {
	                    // remove the sub directory
	                    removeDirectory(ftpClient, dirToList, currentFileName);
	                } else {
	                    // delete the file
	                    boolean deleted = ((FTPClient) ftpClient).deleteFile(filePath);
	                    if (deleted) {
	                        System.out.println("DELETED the file: " + filePath);
	                    } else {
	                        System.out.println("CANNOT delete the file: "
	                                + filePath);
	                    }
	                }
	            }
	 
	            // finally, remove the directory itself
	            boolean removed = ((FTPClient) ftpClient).removeDirectory(dirToList);
	            if (removed) {
	                System.out.println("REMOVED the directory: " + dirToList);
	            } else {
	                System.out.println("CANNOT remove the directory: " + dirToList);
	            }
	        }
	        
	        else { 
	        	boolean removed = ((FTPClient) ftpClient).removeDirectory(dirToList);
	        		if(removed) {
	        			System.out.println("Removed an empty directory");
	        		}
	        		else System.out.println("Couldn't remove that empty directory");
	        }
	        
	    }
    
    
    
    
	public static void main(String[] args) {
	
	 	String server = "eu425.pebblehost.com";
        int port = 21;
        String user = "lilrunnermedic@gmail.com.185377";
        String pass = "h6438ewAYk";
 
        FTPClient ftpClient = null;
        
        
        
        try {
        	ftpClient = new FTPClient();

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            
            System.out.println("I'm in");
            
            removeDirectory(ftpClient, "storage/10min", "");

 
            ftpClient.logout();
            ftpClient.disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
}