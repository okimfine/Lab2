package bookquery;
import java.sql.*;
import java.util.*;


public class DBconnect {
	//test
	public String url = "jdbc:mysql://localhost:3306/lab2";
	public String user = "root";
	public String password = "suju1314";
	/**
	 * 连接数据库
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
	 * 数据库操作
	 * */
	/*插入*/
	public int insert(String sql){
		int i = 0;
		Connection con = getCon();
		
		try{
			PreparedStatement pStm = con.prepareStatement(sql);
			i = pStm.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return i; //返回影响的行数，1为成功。
	}
	/*更新*/
	public int update(String sql){
		int i = 0;
		Connection con = getCon();
		
		try{
			PreparedStatement pStm = con.prepareStatement(sql);
			i = pStm.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return i; //返回影响的行数，1为成功。
	}
	/*选择*/
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
					
			}//将查找到的值写入类，然后返回相应的对象
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	/*删除*/
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