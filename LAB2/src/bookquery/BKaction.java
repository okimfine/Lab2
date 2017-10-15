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
	public Pattern pat = Pattern.compile("\\?");//������ʽ test test test
	public List<String> list = new LinkedList<String>();//��ʼ��������
	public List<String> inilist = new LinkedList<String>();
	ServletRequest sr = ServletActionContext.getRequest();
	HttpServletRequest hsr = (HttpServletRequest) sr;//����û�����
	HttpSession hses = hsr.getSession();//������������Ϣ
	public String username;//��ѯʱ
	public String isbn,title,authorID,publisher,publishDate,price;//the list of book
	public String name,age,country;//the list of author
    static int flag;
    
    /**
     * ��ȡ�û�����
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
     * ������Ϣ
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
     * ���Ĳ�������
     * */
    /*��ѯ*/
    public  String execute() {
        DBconnect connect = new DBconnect();//�������ݿ�
        String sql0 ="select AuthorID from author where Name = ?";
        Matcher m1 = pat.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+ username +'"');
        //MySQL��䣺select AuthorID from author where Name = ��ѯ���������֣�ѡ��
        inilist = connect.select(sql1);//���ݿ�ѡ�����
        if(inilist.size() == 0)
            return "FAIL";//�޽�����أ�����ʧ��
        String sql2 ="select Title from book where AuthorID = ?";
        Matcher m2 = pat.matcher(sql2);
        String sql = m2.replaceFirst('"'+ inilist.get(0) +'"');
        //MySQL��䣺select Title from book where AuthorID = �û������ͼ����Ŀ	��ѡ��
        list = connect.select(sql);
        hses.setAttribute("list", list);
        hses.setAttribute("username",username);
        if(list.size() == 0)
            return "FAIL";//�޽�����أ�����ʧ��
        else
            return "SUCCESS";
    }
    
    /*չʾ��Ϣ*/
    public String detail(){
        DBconnect connect = new DBconnect();
        String sql0 ="select * from author where Name = ?";
        Matcher m1 = pat.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+ username +'"');
        //MySQL��䣺select * from author where Name = ? �����ң�
        inilist = connect.select(sql1);
        hses.setAttribute("inilist", inilist);
        String sql2 ="select * from book where Title = ?";
        Matcher m2 = pat.matcher(sql2);
        String sql3 = m2.replaceFirst('"'+ title +'"');
        //MySQL��䣺select * from book where Title = ? �����ң�	
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
    
    /*���û������ɾ������ťʱ������Ӧ�е�ͼ������ݱ���ɾ��*/
    public String delete(){
        DBconnect connect = new DBconnect();
        String sql0 ="delete from book where ISBN = ?";
        Matcher m1 = pat.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+ isbn +'"');
        //MySQL��䣺delete from book where ISBN = ��Ҫɾ����ͼ���ISBN
        int signal = connect.delete(sql1);
        if(signal == 1)
            return "SUCCESS";
        else
            return "FAIL";
    }
    
    /*�������*/
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
        //MySQL��䣺insert into author(AuthorID,Name,Age,Country) values(authorID,name,age,country)
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

    /*��������*/
    public String addbook(){
        DBconnect connect = new DBconnect();
        flag=1;
        String sql0 = "select * from author where Name = ?";
        Matcher m1 = pat.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+ name +'"');
        //MySQL��䣺select * from author where Name = name
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
            return "ADD";//�������
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
        //MySQL��䣺insert into book(ISBN,Title,AuthorID,Publisher,PublishDate,Price) values(isbn,title,authorID,publisher,publishDate,price)
        int signal = connect.insert(sql);//��������
        if(signal == 1)
            return "SUCCESS";
        else
            return "FAIL";
    }
    
    /*�༭����*/
    public String edit(){
        flag = 0;
        DBconnect connect = new DBconnect();
        String[] sql = new String[6];
        sql[0] = "update book set AuthorID = ?,Publisher = ?,PublishDate = ?,Price = ? where ISBN = ?";//����
        String sql0 = "select * from author where Name = ?";//����
        Matcher m1 = pat.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+ name +'"');
        //MySQL��䣺select * from author where Name = name
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
        //MySQL��䣺update book set AuthorID = authorID,Publisher = publisher,PublishDate = publishDate,Price = price where ISBN = isbn
        int signal = connect.update(sql[5]);
        if(signal == 1)
            return "SUCCESS";
        else
            return "FAIL";
    }
}
