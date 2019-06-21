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

import com.springmvc.dao.AdminShowAllMyLogDao;
import com.springmvc.page.Pager;
import com.springmvc.pojo.LotteryResultPojo;
import com.springmvc.pojo.MyLogPojo;
import com.springmvc.service.AdminShowAllMyLogService;
@Service("adminShowAllMyLogService")
public class AdminShowAllMyLogServiceImpl implements AdminShowAllMyLogService {
	
	@Resource(name="adminShowAllMyLogDao")
	private AdminShowAllMyLogDao adminShowAllMyLogDao;
	
	//添加日志
	public String addMyLog(Object object[]){
		String sucess="";
		String paras[]={"",""};

		for(int i=0;i<adminShowAllMyLogDao.showAllMyLog(paras).size();i++){
			if((Integer.valueOf((String) object[0]))==adminShowAllMyLogDao.showAllMyLog(paras).get(i).getId()){
				sucess="序号不能重复,添加失败";
			}
		}
		if("".equals(sucess)){
			adminShowAllMyLogDao.addMyLog(object);
		}
		return sucess;
	}
	
	//删除日志
	public void deleteMyLog(String paras[]){
		adminShowAllMyLogDao.deleteLotteryResult(paras);
	}
	
	//显示日志
	public ArrayList<MyLogPojo> showAllMyLog(String[] paras){
		ArrayList<MyLogPojo> al=adminShowAllMyLogDao.showAllMyLog(paras);
		return al;
	}
	public Pager<MyLogPojo> searchPager(MyLogPojo myLogPojo, int pageNum,  int pageSize, String[] paras) {     
        Pager<MyLogPojo> pager = new Pager<MyLogPojo>(pageNum, pageSize, showAllMyLog(paras));  
        return pager;  
    }
	
	//更新日志
	public ArrayList<MyLogPojo> beforeUpdateMyLog(String paras[]){
		ArrayList<MyLogPojo> ll=adminShowAllMyLogDao.beforeUpdateMyLog(paras);
		return ll;
	}
	public String updateMyLog(String paras[]){
		adminShowAllMyLogDao.updateMyLog(paras);
		return "更新成功";
	}
	
	//上传文件
	public static int columuSize=31;
	public String readExcel(String filePath){
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
						Cell[] cell=new Cell[31];
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
							for(int j=0;j<paras[count].length;j++){
								if("".equals(paras[count][j])){
									paras[count][j]=0;
								}
							}
						}
						Object object[]={paras[count][0],paras[count][1],paras[count][2],paras[count][3],paras[count][4],paras[count][5],paras[count][6],paras[count][7],paras[count][8],paras[count][9],
		        				paras[count][10],paras[count][11],paras[count][12],paras[count][13],paras[count][14],paras[count][15],paras[count][16],paras[count][17],paras[count][18],paras[count][19],
		        				paras[count][20],paras[count][21],paras[count][22],paras[count][23],paras[count][24],paras[count][25],paras[count][26],paras[count][27],paras[count][28],paras[count][29],
		        				paras[count][30]};
						System.out.println("4:"+paras[count][4]);
						System.out.println("5:"+paras[count][5]);
						adminShowAllMyLogDao.uploadMyLog(object);
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
	public ArrayList<MyLogPojo> exportAllMyLog(String[] paras){
		ArrayList<MyLogPojo> al = adminShowAllMyLogDao.exportAllMyLog(paras);
		return al;
	}
	
	//生成图表
	public int[] showMyLogChart(String[] paras){
		return adminShowAllMyLogDao.showMyLogChart(paras);
	}
}
