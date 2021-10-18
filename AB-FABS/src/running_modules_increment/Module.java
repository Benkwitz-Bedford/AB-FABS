package running_modules_increment;

public class Module {

	protected String modType = "base";
	protected Boolean active = false;

	public void genNext(Bundle sim)
	{
		
	}
	
	public String getModType() {
		return modType;
	}

	
	
	public void setModType(String modType) {
		this.modType = modType;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
}
