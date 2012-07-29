package br.com.chutaum.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

public class Action  {
	
	public Action(Entity en) {
		this.setKey(en.getKey().toString());
	 	this.setId(en.getKey().getName());
		this.setContent((Text) en.getProperty("Content"));
		this.setApp(new App((Integer) Integer.parseInt(en.getProperty("App").toString())));
		this.setDate((Date) en.getProperty("Date"));
		this.setDateMs((Long) en.getProperty("DateMs"));
		this.setIdPolition(Integer.parseInt((en.getProperty("IdPolitician").toString())));
		this.setNamePolitician(en.getProperty("NamePolitician").toString());
	}	
	
	public Action(String idAction, String content, Date date, App app, long poli) {
		this.setContent(content);
		this.setDate(date);
		this.setIdPolition(poli);
		this.setApp(app);
		this.setId(generateActionID(poli, app, idAction));
		this.setDateMs(date.getTime());
	}
	
	public Action() {}
	
	private String Key;
	private String id;
	private Date date;
	private String content;
	private long idPolition;
	private App App;
	private long dateMs;
	private String namePolitician;
	
	public String getNamePolitician() {
		return namePolitician;
	}

	public void setNamePolitician(String namePolitician) {
		this.namePolitician = namePolitician;
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(Text content) {
				this.content = content.getValue();
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public long getIdPolition() {
		return idPolition;
	}
	
	public void setIdPolition(long idPolition) {
		this.idPolition = idPolition;
	}
	
	public App getApp() {
		return App;
	}
	
	public void setApp(App app) {
		this.App = app;
	}

	public long getDateMs() {
		return dateMs;
	}

	public void setDateMs(long dateMs) {
		this.dateMs = dateMs;
	}
	
	public int getMonth() {
			Calendar calendar;
			calendar = Calendar.getInstance();
			SimpleDateFormat currentMonth = new SimpleDateFormat("M");
			calendar.setTimeInMillis(this.getDateMs());
			return Integer.parseInt(currentMonth.format(calendar.getTime()));
	}
	
	public String getMonthText() {
		Calendar calendar;
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(this.getDateMs());
		Locale local = new Locale("pt","BR");
		SimpleDateFormat monthformat = new SimpleDateFormat("MMMM",local);   
		calendar.setTimeInMillis(this.getDateMs());
		return monthformat.format(calendar.getTime());
	}
	
	public String getYear() {
		Calendar calendar;
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(this.getDateMs());
		SimpleDateFormat format = new SimpleDateFormat("yyyy");  
		calendar.setTimeInMillis(this.getDateMs());
		return format.format(calendar.getTime());
	}
	
	public String getFormatDate() {
		Calendar calendar;
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(this.getDateMs());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM");  
		calendar.setTimeInMillis(this.getDateMs());
		return format.format(calendar.getTime());
	}
	
	public String getPhoto() {
		return "/images/facebook.png";
	}
	
	
	public Entity createActionEntity(Politician poli) {
		Entity entity = new Entity(Entitys.PoliticianAction,this.getId(),poli.getKey());
		Text text = new Text(this.getContent());
		entity.setProperty("Content", text);
		entity.setProperty("Date",this.getDate());
		entity.setProperty("DateMs", this.getDateMs());
		entity.setProperty("App",this.getApp().getID());
		entity.setProperty("IdPolitician",poli.getId());
		entity.setProperty("NamePolitician",poli.getName());
		return entity;
	}
	
	private String generateActionID(long poli,App app, String idAction) {
		return app.getID()+"."+poli+"."+idAction;
	}
	
	public Entity createUserActionEntity(User user) {
		Entity entity = new Entity(Entitys.UserAction,this.getId(),KeyFactory.createKey(Entitys.User, user.getEmail()));
		entity.setProperty("DateMs", this.getDateMs());
		return entity;
	}	
		

}
	
