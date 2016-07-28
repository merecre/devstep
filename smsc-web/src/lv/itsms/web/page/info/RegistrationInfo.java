package lv.itsms.web.page.info;

public class RegistrationInfo {

	String txtHeader;
	String txtFirstname;
	String txtLastname;
	String txtEmail;
	String txtLogin;
	String txtPassword;
	String txtBtnSubmit;
	String txtBtnReset;
	String txtLoginHref;
	String txtLoginDesr;


	public String getTxtHeader() {
		return txtHeader;
	}
	public void setTxtHeader(String txtHeader) {
		this.txtHeader = txtHeader;
	}
	public String getTxtFirstname() {
		return txtFirstname;
	}
	public void setTxtFirstname(String txtFirstname) {
		this.txtFirstname = txtFirstname;
	}
	public String getTxtLastname() {
		return txtLastname;
	}
	public void setTxtLastname(String txtLastname) {
		this.txtLastname = txtLastname;
	}
	public String getTxtEmail() {
		return txtEmail;
	}
	public void setTxtEmail(String txtEmail) {
		this.txtEmail = txtEmail;
	}
	public String getTxtLogin() {
		return txtLogin;
	}
	public void setTxtLogin(String txtLogin) {
		this.txtLogin = txtLogin;
	}
	public String getTxtPassword() {
		return txtPassword;
	}
	public void setTxtPassword(String txtPassword) {
		this.txtPassword = txtPassword;
	}
	public String getTxtBtnSubmit() {
		return txtBtnSubmit;
	}
	public void setTxtBtnSubmit(String txtBtnSubmit) {
		this.txtBtnSubmit = txtBtnSubmit;
	}
	public String getTxtBtnReset() {
		return txtBtnReset;
	}
	public void setTxtBtnReset(String txtBtnReset) {
		this.txtBtnReset = txtBtnReset;
	}
	public String getTxtLoginHref() {
		return txtLoginHref;
	}
	public void setTxtLoginHref(String txtLoginHref) {
		this.txtLoginHref = txtLoginHref;
	}
	public String getTxtLoginDesr() {
		return txtLoginDesr;
	}
	public void setTxtLoginDesr(String txtLoginDesr) {
		this.txtLoginDesr = txtLoginDesr;
	}

	@Override
	public String toString() {
		return "RegistrationInfo [txtHeader=" + txtHeader + ", txtFirstname=" + txtFirstname + ", txtLastname="
				+ txtLastname + ", txtEmail=" + txtEmail + ", txtLogin=" + txtLogin + ", txtPassword=" + txtPassword
				+ ", txtBtnSubmit=" + txtBtnSubmit + ", txtBtnReset=" + txtBtnReset + ", txtLoginHref=" + txtLoginHref
				+ ", txtLoginDesr=" + txtLoginDesr + "]";
	}

}
