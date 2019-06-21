package com.springmvc.service.impl;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.springmvc.dao.AdminShowAllLotteryResultDao;
import com.springmvc.page.Pager;
import com.springmvc.pojo.LotteryResultPojo;
import com.springmvc.service.AdminShowAllLotteryResultService;
@Service("adminShowAllLotteryResultService")
public class AdminShowAllLotteryResultServiceImpl implements AdminShowAllLotteryResultService {
	@Resource(name="adminShowAllLotteryResultDao") 
	private AdminShowAllLotteryResultDao adminShowAllLotteryResultDao;

	//Add
	public String addLotteryResult(Object object[]){
		return adminShowAllLotteryResultDao.addLotteryResult(object);
	}
	
	//delete
	public void deleteLotteryResult(String paras[]){
		adminShowAllLotteryResultDao.deleteLotteryResult(paras);
	}
	
	//show
	public ArrayList<LotteryResultPojo> showAllLotteryResult(){
		ArrayList<LotteryResultPojo> al=adminShowAllLotteryResultDao.showAllLotteryResult();
		return al;
	}
	public Pager<LotteryResultPojo> showPager(LotteryResultPojo lotteryResultPojo, int pageNum,  int pageSize) {     
        Pager<LotteryResultPojo> pager = new Pager<LotteryResultPojo>(pageNum, pageSize, showAllLotteryResult());  
        return pager;  
    } 
	
	public ArrayList<LotteryResultPojo> searchLotteryResult(String[] paras){
		ArrayList<LotteryResultPojo> al=adminShowAllLotteryResultDao.searchLotteryResult(paras);
		return al;
	}
	public Pager<LotteryResultPojo> searchPager(LotteryResultPojo lotteryResultPojo, int pageNum,  int pageSize, String[] paras) {     
        Pager<LotteryResultPojo> pager = new Pager<LotteryResultPojo>(pageNum, pageSize, searchLotteryResult(paras));  
        return pager;  
    }
	
	//update
	public ArrayList<LotteryResultPojo> beforeUpdateLotteryResult(String paras[]){
		ArrayList<LotteryResultPojo> ll=adminShowAllLotteryResultDao.beforeUpdateLotteryResult(paras);
		return ll;
	}
	public void updateLotteryResult(String paras[]){
		adminShowAllLotteryResultDao.updateLotteryResult(paras);
	}	
	
	//upload lotteryresult
	public static int columuSize=9;
	public String readExcel(String filePath){
	/*	System.out.println("hello");*/
		String sucess="上传失败";
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
						Cell cell0=row.getCell(0);
						
						Cell cell1=row.getCell(1);
						
						Cell cell2=row.getCell(2);
						
						Cell cell3=row.getCell(3);
						
						Cell cell4=row.getCell(4);
						
						Cell cell5=row.getCell(5);
						
						Cell cell6=row.getCell(6);
						
						Cell cell7=row.getCell(7);
						
						Cell cell8=row.getCell(8);
						
						
						String cellvalue0=getValue(cell0);
						String cellvalue1=getValue(cell1);
						int cellvalue2=Integer.parseInt(getValue(cell2));
						int cellvalue3=Integer.parseInt(getValue(cell3));
						int cellvalue4=Integer.parseInt(getValue(cell4));
						int cellvalue5=Integer.parseInt(getValue(cell5));
						int cellvalue6=Integer.parseInt(getValue(cell6));
						int cellvalue7=Integer.parseInt(getValue(cell7));
						int cellvalue8=Integer.parseInt(getValue(cell8));
						Calendar cal = Calendar.getInstance();  
					    cal.set(2017, 0, 1);  
					    cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf(cellvalue1)-42736);  
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
					    System.out.println(date);
						paras[count][0] = cellvalue0;
						paras[count][1] = date;
						paras[count][2] = cellvalue2;
						paras[count][3] = cellvalue3;
						paras[count][4] = cellvalue4;
						paras[count][5] = cellvalue5;
						paras[count][6] = cellvalue6;
						paras[count][7] = cellvalue7;
						paras[count][8] = cellvalue8;
						count++;
						//System.out.println("hello");
						/*System.out.println(cellvalue0);*/
					}
				}
				in.close();
				for(int i=0;i<count;i++){
				    String sql="insert into lotteryresult (phase,date,red1,red2,red3,red4,red5,blue1,blue2) values(?,?,?,?,?,?,?,?,?)";
			        Object object[]={paras[i][0],paras[i][1],paras[i][2],paras[i][3],paras[i][4],paras[i][5],paras[i][6],paras[i][7],paras[i][8]};
			        adminShowAllLotteryResultDao.uploadLotteryResult(sql, object);
				}
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
				System.out.println("getvalue");
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
	
	//download lotteryresult
	public ArrayList<LotteryResultPojo> exportAllLotteryResult(){
		String sql = "select * from lotteryresult";
		ArrayList<LotteryResultPojo> al = adminShowAllLotteryResultDao.exportAllLotteryResult(sql);
		return al;
	}
	
	//lotteryresultchart
	public int[] showLotteryResultChart(String[] paras,String type){
		int number=0;
		if("red1".equals(type)){
			number=3;
		}else if("red2".equals(type)){
			number=4;
		}else if("red3".equals(type)){
			number=5;
		}else if("red4".equals(type)){
			number=6;
		}else if("red5".equals(type)){
			number=7;
		}else if("blue1".equals(type)){
			number=8;
		}else if("blue2".equals(type)){
			number=9;
		}	
		return adminShowAllLotteryResultDao.showLotteryResultChart(paras,number);
	}
}
