package entity.model;

public class FirebaseBody {
	String to;
	Integer time_to_live = 300;
	Boolean delay_while_idle = true;
	FirebaseNotification notification = new FirebaseNotification();
	String priority = "normal"; // normal atau high
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Integer getTime_to_live() {
		return time_to_live;
	}
	public void setTime_to_live(Integer time_to_live) {
		this.time_to_live = time_to_live;
	}
	public Boolean getDelay_while_idle() {
		return delay_while_idle;
	}
	public void setDelay_while_idle(Boolean delay_while_idle) {
		this.delay_while_idle = delay_while_idle;
	}
	public FirebaseNotification getNotification() {
		return notification;
	}
	public void setNotification(FirebaseNotification notification) {
		this.notification = notification;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
}
