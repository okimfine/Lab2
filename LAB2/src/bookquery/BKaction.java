package bookquery;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

public class BKaction {
	public Pattern pat = Pattern.compile("\\?");//正则表达式 test test test
	public List<String> list = new LinkedList<String>();//初始化空链表
	public List<String> inilist = new LinkedList<String>();
	ServletRequest sr = ServletActionContext.getRequest();
	HttpServletRequest hsr = (HttpServletRequest) sr;//获得用户需求
	HttpSession hses = hsr.getSession();//保存上下文信息
	public String username;//查询时
	public String isbn,title,authorID,publisher,publishDate,price;//the list of book
	public String name,age,country;//the list of author
    static int flag;
    
    /**
     * 获取用户输入
     * */
    public String getusername(){
    	return username;
    }
    
    public String getisbn(){
    	return isbn;
    }
    
    public String gettitle(){
    	return title;
    }
    
    public String getauthorID(){
    	return authorID;
    }
    
    public String getpublisher(){
    	return publisher;
    }
    
    public String getpublishDate(){
    	return publishDate;
    }
    
    public String getprice(){
    	return price;
    }
    
    public String getname(){
    	return name;
    }
    
    public String getage(){
    	return age;
    }
    
    public String getcountry(){
    	return country;
    }

    /**
     * 重置信息
     * */
    public void setusername(String username) {
        this.username = username;
    }
    
    public void setisbn(String ISBN) {
        this.isbn = ISBN;
    }
    
    public void settitle(String Title) {
        this.title = Title;
    }
    
    public void setauthorID(String AuthorID) {
        this.authorID = AuthorID;
    }
    
    public void publisher(String Publisher) {
        this.publisher = Publisher;
    }
    
    public void publishDate(String PublishDate) {
        this.publishDate = PublishDate;
    }
    
    public void setprice(String Price) {
        this.price = Price;
    }
    
    public void setname(String Name) {
        this.name = Name;
    }
    
    public void setage(String Age) {
        this.age = Age;
    }
   
    public void setCountry(String Country) {
        this.country = Country;
    }

    /**
     * 核心操作方法
     * */
    /*查询*/
    public  String execute() {
        DBconnect connect = new DBconnect();//连接数据库
        String sql0 ="select AuthorID from author where Name = ?";
        Matcher m1 = pat.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+ username +'"');
        //MySQL语句：select AuthorID from author where Name = 查询的作者名字（选择）
        inilist = connect.select(sql1);//数据库选择操作
        if(inilist.size() == 0)
            return "FAIL";//无结果返回，查找失败
        String sql2 ="select Title from book where AuthorID = ?";
        Matcher m2 = pat.matcher(sql2);
        String sql = m2.replaceFirst('"'+ inilist.get(0) +'"');
        //MySQL语句：select Title from book where AuthorID = 用户点击的图书题目	（选择）
        list = connect.select(sql);
        hses.setAttribute("list", list);
        hses.setAttribute("username",username);
        if(list.size() == 0)
            return "FAIL";//无结果返回，查找失败
        else
            return "SUCCESS";
    }
    
    /*展示信息*/
    public String detail(){
        DBconnect connect = new DBconnect();
        String sql0 ="select * from author where Name = ?";
        Matcher m1 = pat.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+ username +'"');
        //MySQL语句：select * from author where Name = ? （查找）
        inilist = connect.select(sql1);
        hses.setAttribute("inilist", inilist);
        String sql2 ="select * from book where Title = ?";
        Matcher m2 = pat.matcher(sql2);
        String sql3 = m2.replaceFirst('"'+ title +'"');
        //MySQL语句：select * from book where Title = ? （查找）	
        list = connect.select(sql3);
        hses.setAttribute("list", list);
        if(list.size() == 0)
            return "FAIL";
        else
            return "SUCCESS";
    }
    
    public String gotoedit(){
        return "SUCCESS";
    } 
    
    /*当用户点击“删除”按钮时，将对应行的图书从数据表中删除*/
    public String delete(){
        DBconnect connect = new DBconnect();
        String sql0 ="delete from book where ISBN = ?";
        Matcher m1 = pat.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+ isbn +'"');
        //MySQL语句：delete from book where ISBN = 所要删除的图书的ISBN
        int signal = connect.delete(sql1);
        if(signal == 1)
            return "SUCCESS";
        else
            return "FAIL";
    }
    
    /*添加作者*/
    public String addauthor(){
        DBconnect connect = new DBconnect();
        String[] sql = new String[5];
        sql[0]="insert into author(AuthorID,Name,Age,Country) values(?,?,?,?)";
        Matcher m1 = pat.matcher(sql[0]);
        sql[1] = m1.replaceFirst('"'+ authorID +'"');
        m1 = pat.matcher(sql[1]);
        sql[2] = m1.replaceFirst('"'+ name +'"');
        m1 = pat.matcher(sql[2]);
        sql[3] = m1.replaceFirst('"'+ age +'"');
        m1 = pat.matcher(sql[3]);
        sql[4] = m1.replaceFirst('"'+ country +'"');
        //MySQL语句：insert into author(AuthorID,Name,Age,Country) values(authorID,name,age,country)
        int signal = connect.insert(sql[4]);
        if(signal == 1){
            if(flag == 0)
                return "SUCCESS0";
            else
                return "SUCCESS1";
        }
        else
            return "FAIL";
    }

    /*增加新书*/
    public String addbook(){
        DBconnect connect = new DBconnect();
        flag=1;
        String sql0 = "select * from author where Name = ?";
        Matcher m1 = pat.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+ name +'"');
        //MySQL语句：select * from author where Name = name
        list = connect.select(sql1);
        inilist = connect.select("select AuthorID from author");
        if(list.size() == 0){
            if(inilist.size() == 0)
                authorID = "1";
            else
                authorID = String.valueOf(inilist.size());
        }
        else
            authorID = list.get(0);
        if(list.size()==0)
            return "ADD";//添加作者
        String sql = "insert into book(ISBN,Title,AuthorID,Publisher,PublishDate,Price) values(?,?,?,?,?,?)";
        m1 = pat.matcher(sql);
        sql = m1.replaceFirst('"'+ isbn +'"');
        m1 = pat.matcher(sql);
        sql = m1.replaceFirst('"'+ title +'"');
        m1 = pat.matcher(sql);
        sql = m1.replaceFirst('"'+ authorID +'"');
        m1 = pat.matcher(sql);
        sql = m1.replaceFirst('"'+ publisher +'"');
        m1 = pat.matcher(sql);
        sql = m1.replaceFirst('"'+ publishDate +'"');
        m1 = pat.matcher(sql);
        sql = m1.replaceFirst('"'+ price +'"');
        //MySQL语句：insert into book(ISBN,Title,AuthorID,Publisher,PublishDate,Price) values(isbn,title,authorID,publisher,publishDate,price)
        int signal = connect.insert(sql);//更新数据
        if(signal == 1)
            return "SUCCESS";
        else
            return "FAIL";
    }
    
    /*编辑更新*/
    public String edit(){
        flag = 0;
        DBconnect connect = new DBconnect();
        String[] sql = new String[6];
        sql[0] = "update book set AuthorID = ?,Publisher = ?,PublishDate = ?,Price = ? where ISBN = ?";//更新
        String sql0 = "select * from author where Name = ?";//查找
        Matcher m1 = pat.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+ name +'"');
        //MySQL语句：select * from author where Name = name
        list = connect.select(sql1);
        inilist = connect.select("select AuthorID from author");
        m1 = pat.matcher(sql[0]);
        if(list.size() == 0){
            if(inilist.size() == 0)
                authorID = "1";
            else
            	authorID = String.valueOf(inilist.size());
        }
        else
        	authorID = list.get(0);
        if(list.size() == 0)
            return "ADD";
        sql[1] = m1.replaceFirst('"'+ authorID +'"');
        m1=pat.matcher(sql[1]);
        sql[2] = m1.replaceFirst('"'+ publisher +'"');
        m1=pat.matcher(sql[2]);
        sql[3] = m1.replaceFirst('"'+ publishDate +'"');
        m1=pat.matcher(sql[3]);
        sql[4] = m1.replaceFirst('"'+ price +'"');
        m1=pat.matcher(sql[4]);
        sql[5] = m1.replaceFirst('"'+ isbn +'"');
        //MySQL语句：update book set AuthorID = authorID,Publisher = publisher,PublishDate = publishDate,Price = price where ISBN = isbn
        int signal = connect.update(sql[5]);
        if(signal == 1)
            return "SUCCESS";
        else
            return "FAIL";
    }
}
