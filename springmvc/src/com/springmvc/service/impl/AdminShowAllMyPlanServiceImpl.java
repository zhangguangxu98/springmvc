package com.springmvc.service.impl;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.springmvc.dao.AdminShowAllMyPlanDao;
import com.springmvc.page.Pager;
import com.springmvc.pojo.MyLogPojo;
import com.springmvc.pojo.MyPlanPojo;
import com.springmvc.service.AdminShowAllMyPlanService;
@Service("adminShowAllMyPlanService")
public class AdminShowAllMyPlanServiceImpl implements AdminShowAllMyPlanService {
	
	@Resource(name="adminShowAllMyPlanDao")
	private AdminShowAllMyPlanDao adminShowAllMyPlanDao;
	
	//添加日志
	public String addMyPlan(Object object[]){
		String sucess="";
		String paras[]={"",""};
		String sql="INSERT INTO MYPLAN (ID, STARTDATE, ENDDATE, PLAN, CONTENT, ASSESSMENT) VALUES (?, ?, ?, ?, ?, ?)";
		String showsql = "SELECT * FROM MYPLAN ORDER BY ID ASC";
		String searchsql="SELECT * FROM MYPLAN WHERE STARTDATE>=? AND ENDDATE<=? ORDER BY ID ASC";
		for(int i=0;i<adminShowAllMyPlanDao.showAllOrExportMyPlan(paras,showsql,searchsql).size();i++){
			if((Integer.valueOf((String) object[0]))==adminShowAllMyPlanDao.showAllOrExportMyPlan(paras,showsql,searchsql).get(i).getId()){
				sucess="序号不能重复,添加失败";
			}
		}
		if("".equals(sucess)){
			adminShowAllMyPlanDao.addOrUploadMyPlan(object,sql);
		}
		return sucess;
	}
	
	//删除日志
	public void deleteMyPlan(String paras[]){
		String sql="DELETE FROM MYPLAN WHERE ID=?";
		adminShowAllMyPlanDao.deleteOrUpdateMyPlan(paras,sql);
	}
	
	//显示日志
	public ArrayList<MyPlanPojo> showAllMyPlan(String[] paras){
		String sql = "SELECT * FROM MYPLAN ORDER BY ID ASC";
		String searchsql="SELECT * FROM MYPLAN WHERE STARTDATE>=? AND ENDDATE<=? ORDER BY ID ASC";
		ArrayList<MyPlanPojo> al=adminShowAllMyPlanDao.showAllOrExportMyPlan(paras,sql,searchsql);
		return al;
	}
	public Pager<MyPlanPojo> searchPager(MyPlanPojo myLogPojo, int pageNum,  int pageSize, String[] paras) {     
        Pager<MyPlanPojo> pager = new Pager<MyPlanPojo>(pageNum, pageSize, showAllMyPlan(paras));  
        return pager;  
    }
	
	//更新日志
	public ArrayList<MyPlanPojo> beforeUpdateMyPlan(String paras[]){
		String sql="SELECT * FROM MYPLAN WHERE ID=?";
		ArrayList<MyPlanPojo> ll=adminShowAllMyPlanDao.beforeUpdateMyPlan(paras,sql);
		return ll;
	}
	public String updateMyPlan(String paras[]){
		String sql="UPDATE MYPLAN SET STARTDATE=?,ENDDATE=?,PLAN=?,CONTENT=?,ASSESSMENT=? WHERE ID=?";
		adminShowAllMyPlanDao.deleteOrUpdateMyPlan(paras,sql);
		return "更新成功";
	}
	
	//上传文件
	public static int columuSize=6;
	public String readExcel(String filePath){
		String sucess="上传失败";
		String sql="INSERT INTO MYPLAN (ID, STARTDATE, ENDDATE, PLAN, CONTENT, ASSESSMENT) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			Workbook workBook = null;
			FileInputStream in = new FileInputStream(filePath);
			try {
				workBook = new XSSFWorkbook(in);
			} catch (Exception ex) {
				workBook = new HSSFWorkbook(in);
			}
			int column=columuSize;
			Sheet sheet = workBook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			System.out.println("gong you ====" + rows);
			Row roww = sheet.getRow(1);
			System.out.println("gong you ====" + roww.getLastCellNum());
			Object paras[][] = new Object[rows][column];
			int count = 0;
			if (rows > 0) {
				for (int r = 1; r < rows; r++) {
					Row row = sheet.getRow(r);
					if (row != null) {
						Cell[] cell=new Cell[6];
						for(int i=0;i<cell.length;i++){
							cell[i]=row.getCell(i);
							paras[count][i] = getValue(cell[i]);
							if(i==1){
								Calendar cal = Calendar.getInstance();  
							    cal.set(2017, 0, 1);  
							    cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf((String) paras[count][1])-42736);  
							    String date=(cal.get(Calendar.YEAR) + "-");
							    if((cal.get(Calendar.MONTH))<9){
							    	date+="0"+(cal.get(Calendar.MONTH) + 1 + "-");
							    }else{
							    	date+=cal.get(Calendar.MONTH) + 1 + "-";
							    }
							    if((cal.get(Calendar.DAY_OF_MONTH))<10){
							    	date+="0"+(cal.get(Calendar.DAY_OF_MONTH));
							    }else{
							    	date+=cal.get(Calendar.DAY_OF_MONTH);
							    }
							    paras[count][1]=date;
							}
							if(i==2){
								Calendar cal = Calendar.getInstance();  
							    cal.set(2017, 0, 1);  
							    cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf((String) paras[count][2])-42736);  
							    String date=(cal.get(Calendar.YEAR) + "-");
							    if((cal.get(Calendar.MONTH))<9){
							    	date+="0"+(cal.get(Calendar.MONTH) + 1 + "-");
							    }else{
							    	date+=cal.get(Calendar.MONTH) + 1 + "-";
							    }
							    if((cal.get(Calendar.DAY_OF_MONTH))<10){
							    	date+="0"+(cal.get(Calendar.DAY_OF_MONTH));
							    }else{
							    	date+=cal.get(Calendar.DAY_OF_MONTH);
							    }
							    paras[count][2]=date;
							}
							for(int j=0;j<paras[count].length;j++){
								if("".equals(paras[count][j])){
									paras[count][j]=0;
								}
							}
						}
						Object object[]={paras[count][0],paras[count][1],paras[count][2],paras[count][3],paras[count][4],paras[count][5]};
						adminShowAllMyPlanDao.addOrUploadMyPlan(object, sql);
						count++;
					}
				}
				in.close();
				sucess="上传成功";
			}
		}catch (Exception ex) {
				ex.printStackTrace();
		}
		return sucess;
	}
	public String getValue(Cell cell) {
		String value = "";
		if(cell==null){
			value="";
		}else{
			if(cell.getCellType()==0){
				value=(int)cell.getNumericCellValue()+"";
			}else if(cell.getCellType()==1){
			    value = cell.getStringCellValue();
			}else if(cell.getCellType()==2){
				value="";
			}else if(cell.getCellType()==3){
				value="";
			}else if(cell.getCellType()==4){
				value="";
			}else if(cell.getCellType()==5){
				value="";
			}else{
				value="";
			}
		}
		return value;
	}
	
	//下载文件
	public ArrayList<MyPlanPojo> exportAllMyPlan(String[] paras){
		String showsql = "SELECT * FROM MYPLAN ORDER BY ID ASC";
		String searchsql="SELECT * FROM MYPLAN WHERE STARTDATE>=? AND ENDDATE<=? ORDER BY ID ASC";
		ArrayList<MyPlanPojo> al = adminShowAllMyPlanDao.showAllOrExportMyPlan(paras, showsql, searchsql);
		return al;
	}
	
	//生成图表
	public Map<String,Integer> showMyPlanChart(String[] paras){
		String showsql = "SELECT * FROM MYPLAN ORDER BY ID ASC";
		String searchsql="SELECT * FROM MYPLAN WHERE STARTDATE>=? AND ENDDATE<=? ORDER BY ID ASC";
		return adminShowAllMyPlanDao.showMyPlanChart(paras,showsql,searchsql);
	}
}
