package br.com.chutaum.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.codehaus.jackson.annotate.JsonIgnore;

import br.com.chutaum.politician.PoliticianController;
import br.com.chutaum.utils.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
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
	
	

	
	public Action(String idAction, String content, Date date, App app, int poli) {
		Politician politician = PoliticianController.findPolitician(poli);
		this.setContent(content);
		this.setDate(date);
		this.setIdPolition(poli);
		this.setApp(app);
		this.setId(generateActionID(poli, app, idAction));
		this.setDateMs(date.getTime());
		this.setPolitician(politician);
	}
	
	public Action() {}
	
	private String Key;
	private String id;
	private Date date;
	private String content;
	private long idPolition;
	private Politician politician;
	private App App;
	private long dateMs;
	private String namePolitician;
	
	public String getNamePolitician() {
		return namePolitician;
	}

	public void setNamePolitician(String namePolitician) {
		this.namePolitician = namePolitician;
	}

	@JsonIgnore
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
	
	public Politician getPolitician() {
		return politician;
	}



	public void setPolitician(Politician politician) {
		this.politician = politician;
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
		return "/images/politician-photos/"+this.getIdPolition()+".jpg";
	}
	
	public boolean save() {
		Entity entity = this.createActionEntity();
 		Util.persistEntity(entity);
 		Iterable<Entity> array = PoliticianController.politicianFollow(this.getPolitician());
 		for (Entity en : array) {
	 		Entity userAction =	this.createUserActionEntity(new User(en.getProperty("User").toString()));
	 		Util.persistEntity(userAction);	
 		}
 		return true;
	}
	
	public boolean isSaved() {
		com.google.appengine.api.datastore.Key parent = new KeyFactory.Builder(Entitys.Politician, Integer.parseInt(this.getId().split("\\.")[1])).addChild(Entitys.PoliticianAction, id).getKey();	
		Entity en = Util.findEntityAndAddCache(parent);
		if (en!=null){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean destroy() {
		return true;
	}
	
	@Deprecated
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
	
	public Entity createActionEntity() {
		Entity entity = new Entity(Entitys.PoliticianAction,this.getId(),this.getPolitician().getKey());
		Text text = new Text(this.getContent());
		entity.setProperty("Content", text);
		entity.setProperty("Date",this.getDate());
		entity.setProperty("DateMs", this.getDateMs());
		entity.setProperty("App",this.getApp().getID());
		entity.setProperty("IdPolitician",this.getPolitician().getId());
		entity.setProperty("NamePolitician",this.getPolitician().getName());
		return entity;
	}
	
	private String generateActionID(int poli,App app, String idAction) {
		return app.getID()+"."+poli+"."+idAction;
	}
	
	public Entity createUserActionEntity(User user) {
		Entity entity = new Entity(Entitys.UserAction,this.getId(),KeyFactory.createKey(Entitys.User, user.getEmail()));
		entity.setProperty("DateMs", this.getDateMs());
		return entity;
	}	
		

}
	
