package bookquery;
import java.sql.*;
import java.util.*;


public class DBconnect {
	//test
	public String url = "jdbc:mysql://localhost:3306/lab2";
	public String user = "root";
	public String password = "suju1314";
	/**
	 * �������ݿ�
	 * */
	public Connection getCon(){
		Connection con = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		try{
			con = DriverManager.getConnection(url,user,password);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return con;
	}
	/**
	 * ���ݿ����
	 * */
	/*����*/
	public int insert(String sql){
		int i = 0;
		Connection con = getCon();
		
		try{
			PreparedStatement pStm = con.prepareStatement(sql);
			i = pStm.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return i; //����Ӱ���������1Ϊ�ɹ���
	}
	/*����*/
	public int update(String sql){
		int i = 0;
		Connection con = getCon();
		
		try{
			PreparedStatement pStm = con.prepareStatement(sql);
			i = pStm.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return i; //����Ӱ���������1Ϊ�ɹ���
	}
	/*ѡ��*/
	public List<String> select(String sql){
		int i;
		Connection con = getCon();
		List<String> list = new LinkedList<String>();		
		try{
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				for(i = 1;i <= rs.getMetaData().getColumnCount();i++){
					list.add(rs.getString(i));
				}
					
			}//�����ҵ���ֵд���࣬Ȼ�󷵻���Ӧ�Ķ���
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	/*ɾ��*/
	public int delete(String sql){
		int i = 0;
		Connection con = getCon();
		
		try{
			Statement stm = con.createStatement();
			i = stm.executeUpdate(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return i;
	}
}