package lv.itsms.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lv.itsms.web.page.info.LoginInfo;
import lv.itsms.web.page.info.SmsPanelInfo;
import lv.itsms.web.service.DBDAOFactory;
import lv.itsms.web.service.SmsPanelInfoDAO;

public class JDBCSmsPanelInfoDAO implements SmsPanelInfoDAO {

	private final static String DB_TABLE = "web_content.content_smspanel";

	private Connection connection;

	private DBDAOFactory factory;

	SmsPanelInfo smsPanelInfo;
	
	public JDBCSmsPanelInfoDAO(DBDAOFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public SmsPanelInfo getSmsPanelInfoByLanguage(String language) throws Exception {
		connection = factory.createConnection();

		PreparedStatement statement = connection.prepareStatement(
				"SELECT * "
						+ "FROM " + DB_TABLE 
						+ " WHERE "	+ "lng='" + language + "'");
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			populateData(resultSet);
		}

		resultSet.close();
		statement.close();
		factory.closeConnection(connection);


		return smsPanelInfo;
	}
	private void populateData (ResultSet resultSet) throws SQLException {
		smsPanelInfo = new SmsPanelInfo();
		smsPanelInfo.setLng(resultSet.getString("lng"));
		smsPanelInfo.setTxtGroupList(resultSet.getString("txt_group_list"));
		smsPanelInfo.setTxtNewGroup(resultSet.getString("txt_new_group"));
		smsPanelInfo.setTxtEditGroup(resultSet.getString("txt_edit_group"));
		smsPanelInfo.setTxtAllCheckbox(resultSet.getString("txt_all_checkbox"));
		smsPanelInfo.setTxtGroupDescription(resultSet.getString("txt_group_description"));
		smsPanelInfo.setTxtGroupMessage(resultSet.getString("txt_message"));
		smsPanelInfo.setTxtPhonenumbers(resultSet.getString("txt_phone_list"));
		smsPanelInfo.setTxtSendDate(resultSet.getString("txt_send_date"));
		smsPanelInfo.setTxtSendTime(resultSet.getString("txt_send_time"));
		smsPanelInfo.setTxtSender(resultSet.getString("txt_sender"));
		smsPanelInfo.setTxtPhoneNumber(resultSet.getString("txt_phonenumber"));
		smsPanelInfo.setBtnSend(resultSet.getString("btn_send"));
		smsPanelInfo.setTxtNewMessage(resultSet.getString("txt_new_message"));
		smsPanelInfo.setTxtTitleGroups(resultSet.getString("txt_title_groups"));
		smsPanelInfo.setTxtAddRow(resultSet.getString("txt_add_row"));
		smsPanelInfo.setTxtRemoveRow(resultSet.getString("txt_remove_row"));
		smsPanelInfo.setTxtDeleteSelected(resultSet.getString("txt_delete_selected"));
		smsPanelInfo.setTxtTitleImport(resultSet.getString("txt_title_import"));
		smsPanelInfo.setTxtBtnImport(resultSet.getString("btn_import"));
		smsPanelInfo.setTxtBtnToSelectFile(resultSet.getString("btn_select_file"));
		smsPanelInfo.setTxtCharCounterMsg(resultSet.getString("txt_char_counter_msg"));
		smsPanelInfo.setTxtCharInformation(resultSet.getString("txt_char_information"));
	}
}
