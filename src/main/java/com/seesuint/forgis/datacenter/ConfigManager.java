package com.seesuint.forgis.datacenter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;
import java.util.Scanner;
import java.util.Base64.Decoder;

public class ConfigManager {
    public String License = null;
	public String Path = null;
	public String DbType = null;
	public String Host = null;
	public String Port = null;
	public String Database = null;
	public String Id = null;
	public String Password = null;
	public String Schema = null;
	public Boolean Index = null;
	public String Lang = null;
	public String Coordsys = null;

	public String getPath() {
		Path = this.getClass().getResource("/DataCenter.ini").getFile();
		// System.getProperty("user.dir").toString();
		// init = conf.getPath()+"\\DataCenter.ini"
		// System.out.println(Path);
		return Path;
	}
	public String getDecoderText(String str) throws Exception {
		byte[] decodedBytes = Base64.getDecoder().decode(str);
		String decodedString = new String(decodedBytes);

		return decodedString;
	}


	public String[] getLicense() {
		String LicenseFile = this.getClass().getResource("/forgis.lic").getFile();
		String[] arr =null;
	       try{
	            //파일 객체 생성
	            File file = new File(LicenseFile);
	            //스캐너로 파일 읽기
	            FileReader filereader = new FileReader(file);
	            //입력 버퍼 생성
	            BufferedReader bufReader = new BufferedReader(filereader);
	            String line = "";
	            while((line = bufReader.readLine()) != null){
	                System.out.println("---"+line);
	                /* base64 encoding */
	                try { // (디코딩은 예외 처리가 필요)

	                    License =getDecoderText(line); // decode
                         arr = License.split("/");
	                    System.out.println(License); // 디코딩 결과 출력(Safe1234)
	                } catch (IOException e) {
	                    e.printStackTrace();
	                } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	            //.readLine()은 끝에 개행문자를 읽지 않는다.
	            bufReader.close();


	            //System.out.println(scan.useDelimiter("\\z").next());
	        }catch (FileNotFoundException e) {
	            // TODO: handle exception
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return arr;
	}

	public void conf(Boolean Indexer, String lang) {
		try {
			Properties p = new Properties();

			// ini 파일 읽기
			p.load(new FileInputStream(getPath()));

			setDbType(p.getProperty("dbtype").trim());
			setHost(p.getProperty("ip").trim());
			setPort(p.getProperty("port").trim());
			setDatabase(p.getProperty("database").trim());
			setId(p.getProperty("id").trim());
			setPassword(p.getProperty("password").trim());
			setSchema(p.getProperty("schema").trim());
			setCoordsys(p.getProperty("usercoordsys").trim());
			setIndex(Indexer);
			setLang(lang);
			// Key 값 저장

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public String[] SetLangList() throws FileNotFoundException, IOException {
		String[] arrLang = null;
		Properties p = new Properties();

		// ini 파일 읽기
		p.load(new FileInputStream(getPath()));
		String LangList = p.getProperty("systemlang");
		arrLang = LangList.split(",");

		return arrLang;
	}

	public String getDbType() {
		return DbType;
	}

	public void setDbType(String dbType) {
		DbType = dbType;
	}

	public String getHost() {
		return Host;
	}

	public void setHost(String host) {
		Host = host;
	}

	public String getPort() {
		return Port;
	}

	public void setPort(String port) {
		Port = port;
	}

	public String getDatabase() {
		return Database;
	}

	public void setDatabase(String database) {
		Database = database;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getSchema() {
		return Schema;
	}

	public void setSchema(String schema) {
		Schema = schema;
	}

	public void setPath(String path) {
		Path = path;
	}

	public Boolean getIndex() {
		return Index;
	}

	public void setIndex(Boolean index) {
		Index = index;
	}

	public String getLang() {
		return Lang;
	}

	public void setLang(String lang) {
		Lang = lang;
	}

	public String getCoordsys() {
		return Coordsys;
	}

	public void setCoordsys(String coordsys) {
		Coordsys = coordsys;
	}

}
