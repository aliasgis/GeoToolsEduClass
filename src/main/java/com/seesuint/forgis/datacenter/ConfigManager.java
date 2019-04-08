package com.seesuint.forgis.datacenter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

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
	public void conf(Boolean Indexer, String lang) {
		try {
			Properties p = new Properties();

			// ini 파일 읽기
			p.load(new FileInputStream(getPath()));

			setDbType(p.getProperty("dbtype"));
			setHost(p.getProperty("ip"));
			setPort(p.getProperty("port"));
			setDatabase(p.getProperty("database"));
			setId(p.getProperty("id"));
			setPassword(p.getProperty("password"));
			setSchema(p.getProperty("schema"));
			setCoordsys(p.getProperty("usercoordsys"));
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
