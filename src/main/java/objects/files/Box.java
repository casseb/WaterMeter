package objects.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.box.sdk.BoxConfig;
import com.box.sdk.BoxDeveloperEditionAPIConnection;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxFolder.Info;
import com.box.sdk.BoxItem;
import com.box.sdk.BoxResource;
import com.box.sdk.BoxSharedLink.Access;
import com.box.sdk.BoxSharedLink.Permissions;
import com.box.sdk.BoxUser;
import com.box.sdk.IAccessTokenCache;
import com.box.sdk.InMemoryLRUAccessTokenCache;
import com.mchange.io.FileUtils;

import access.AccessConfiguration;

public class Box {

	static AccessConfiguration access = new AccessConfiguration();
	
	private static final String USER_ID = access.getBoxIdUsername();
	private static final int MAX_DEPTH = 1;
	private static final int MAX_CACHE_ENTRIES = 100;
	private BoxDeveloperEditionAPIConnection api;
	private BoxFolder rootFolder;

	public Box() {
		// Turn off logging to prevent polluting the output.
		Logger.getLogger("com.box.sdk").setLevel(Level.OFF);

		// It is a best practice to use an access token cache to prevent
		// unneeded requests to Box for access tokens.
		// For production applications it is recommended to use a distributed
		// cache like Memcached or Redis, and to
		// implement IAccessTokenCache to store and retrieve access tokens
		// appropriately for your environment.
		IAccessTokenCache accessTokenCache = new InMemoryLRUAccessTokenCache(MAX_CACHE_ENTRIES);

		Reader reader;
		try {

			reader = new FileReader("src/main/java/access/boxConfig.json");
			BoxConfig boxConfig;
			boxConfig = BoxConfig.readFrom(reader);
			this.api = BoxDeveloperEditionAPIConnection.getAppUserConnection(USER_ID, boxConfig, accessTokenCache);
			BoxUser.Info userInfo = BoxUser.getCurrentUser(api).getInfo();

		} catch (IOException e) {
			e.printStackTrace();
		}

		this.rootFolder = BoxFolder.getRootFolder(api);

	}

	public String addFileByTelegram(BoxFileObject boxFileObject, BoxFolderObject boxFolderObject) {

		try {
			URL url = new URL(boxFileObject.getTelegramLink());
			File file = downloadFileByURL(url, boxFileObject.getName());
			return addFile(file, boxFileObject.getName(),boxFolderObject);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public String addFolderByTelegram(BoxFolderObject boxFolderObject, BoxFolderObject root) {
		
		return addFolder(boxFolderObject.getName(),root);
		

	}

	public String addFile(File file, String name, BoxFolderObject boxFolderObject) {
		System.out.println("inserindo arquivo");
		BoxFolder folder = new BoxFolder(api, boxFolderObject.getBoxId());
		for (com.box.sdk.BoxItem.Info info : folder) {
			if(info.getName().equals(name)){
				BoxFile boxFile = new BoxFile(api, info.getID());
				boxFile.delete();
			}
		}
		FileInputStream stream;
		try {
			stream = new FileInputStream(file);
			BoxFile.Info uploaded = folder.uploadFile(stream, name);
			BoxFile boxFile2 = uploaded.getResource();

			stream.close();
			return boxFile2.getID();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public String addFolder(String name, BoxFolderObject boxFolderObject) {
		BoxFolder folder = null;
		
		if(boxFolderObject == null){
			folder = rootFolder;
		}else{
			folder = new BoxFolder(api, boxFolderObject.getBoxId());
		}
		
		Info info = folder.createFolder(name);
		return info.getID();

	}

	public File downloadFileByURL(URL url, String name) {
		URLConnection connection;
		try {
			connection = url.openConnection();
			InputStream in = connection.getInputStream();
			File file = new File(name);
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buf = new byte[512];
			while (true) {
				int len = in.read(buf);
				if (len == -1) {
					break;
				}
				fos.write(buf, 0, len);
			}
			in.close();
			fos.flush();
			fos.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public File getFileByBox(BoxFileObject boxFileObject) {

		BoxFile boxFile = new BoxFile(api,boxFileObject.getBoxId());
		return downloadFileByURL(boxFile.getDownloadURL(), boxFileObject.getName());
	}


}
