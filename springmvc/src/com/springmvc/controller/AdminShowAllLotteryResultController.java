package com.springmvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.solidfire.gson.Gson;
import com.springmvc.page.Pager;
import com.springmvc.pojo.LotteryResultPojo;
import com.springmvc.service.AdminShowAllLotteryResultService;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


@Controller("AdminShowAllLotteryResultController")
public class AdminShowAllLotteryResultController {
	
	@Resource(name="adminShowAllLotteryResultService") 
	private AdminShowAllLotteryResultService adminShowAllLotteryResultService;

	//add
	@RequestMapping(value="/adminaddlotteryresult.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminAddLotteryResult(HttpServletRequest request,HttpServletResponse response){
		String phase = request.getParameter("phase");
		String date = request.getParameter("date");
        String red1 = request.getParameter("red1");
        String red2 = request.getParameter("red2");
        String red3 = request.getParameter("red3");
        String red4 = request.getParameter("red4");
        String red5 = request.getParameter("red5");
        String blue1 = request.getParameter("blue1");
        String blue2 = request.getParameter("blue2");
        
		Object object[]={phase,date,red1,red2,red3,red4,red5,blue1,blue2};
		adminShowAllLotteryResultService.addLotteryResult(object);
		return new ModelAndView("redirect:/adminshowalllotteryresult.do?pageNum=1");
	}
	
	//delete
	@RequestMapping(value="/admindeletelotteryresult.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminDeleteLotteryResult(HttpServletRequest request,HttpServletResponse response){
		String[] phase = request.getParameterValues("tduCheckBox");
		System.out.println("phase"+phase);
		if(null==phase||"".equals(phase)){
			return new ModelAndView("redirect:/adminshowalllotteryresult.do?pageNum=1");
		}
		for(int i=0;i<phase.length;i++){
			String paras[]={phase[i]};
			System.out.println(phase[i]);
			adminShowAllLotteryResultService.deleteLotteryResult(paras);
		}
		return new ModelAndView("redirect:/adminshowalllotteryresult.do?pageNum=1");
	}
	
	//show
	@RequestMapping(value="/adminshowalllotteryresult.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminShowAllLotteryResult(HttpServletRequest request,HttpServletResponse response){
		String pageNumString = request.getParameter("pageNum");
		String phase1 = request.getParameter("startdate");
		String phase2 = request.getParameter("enddate");
		int pageNum=Integer.valueOf(pageNumString);
		String paras[]={phase1,phase2};
		final int pageSize=10;
		
		LotteryResultPojo lotteryResultPojo=new LotteryResultPojo();
		Pager pager=new Pager();
		
		if(null!=phase1&&""!=phase2){
			pager = adminShowAllLotteryResultService.searchPager(lotteryResultPojo, pageNum, pageSize,paras);
		}else{
			pager = adminShowAllLotteryResultService.showPager(lotteryResultPojo, pageNum, pageSize);
		}
		/*Map mp = new HashMap();
		mp.put("showalllotteryresultlist", showalllotteryresultlist);
		mp.put("pager", pager);
		其中第一个参数为url,第二个参数为要传递的数据的key,第三个参数为数据对象。在这里要注意的是:数据是默认被存放在request中的。*/
		return new ModelAndView("/adminlotteryresult/adminshowalllotteryresult","pager",pager);
	}
	
	//update
	@RequestMapping(value="/adminbeforeupdatelotteryresult.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView beforeUpdateLotteryResult(HttpServletRequest request,HttpServletResponse response){
		String phase = request.getParameter("phase");
		String paras[]={phase};
		
		ArrayList<LotteryResultPojo> adminbeforeupdatelotteryresultlist=adminShowAllLotteryResultService.beforeUpdateLotteryResult(paras);
		return new ModelAndView("/adminlotteryresult/adminbeforeupdatelotteryresult","adminbeforeupdatelotteryresultlist",adminbeforeupdatelotteryresultlist);
	}
	@RequestMapping(value="/adminupdatelotteryresult.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView updateLotteryResult(HttpServletRequest request,HttpServletResponse response){
		String phase = request.getParameter("phase");
		String date = request.getParameter("date");
        String red1 = request.getParameter("red1");
        String red2 = request.getParameter("red2");
        String red3 = request.getParameter("red3");
        String red4 = request.getParameter("red4");
        String red5 = request.getParameter("red5");
        String blue1 = request.getParameter("blue1");
        String blue2 = request.getParameter("blue2");
		String paras[]={date,red1,red2,red3,red4,red5,blue1,blue2,phase};
		
		adminShowAllLotteryResultService.updateLotteryResult(paras);
		return new ModelAndView("redirect:/adminshowalllotteryresult.do?pageNum=1");
	}
	
	//upload lotteryresult
	@RequestMapping(value="/adminuploadlotteryresult.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView UploadExcel(@RequestParam("myFile") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String myFileFileName=file.getOriginalFilename();
		String uploadPath=request.getServletContext().getRealPath("/upload"); 
		
		String error="文件不能为空";
				
		//System.out.println(uploadPath);
		//System.out.println(myFileContentType);
		//System.out.println("upload FileName "+myFileFileName);
		if(file.getName()!=null&&file.isEmpty()==false){
			InputStream in=file.getInputStream();
			File saveDir=new File(uploadPath);
			if(!saveDir.exists())
			{
				saveDir.mkdirs();
			}
			File target=new File(uploadPath,myFileFileName);
			OutputStream out=new FileOutputStream(target);
			byte []buffer=new byte[1024];
			int length=0;
			while((length=in.read(buffer))>0)
			{
				out.write(buffer, 0,length);
			}
			in.close();
			out.close();
		}else{
			return new ModelAndView("/adminlotteryresult/adminupanddownloadlotteryresult","error",error);
		}	
		String sucess=adminShowAllLotteryResultService.readExcel(uploadPath+"/"+myFileFileName);
		return new ModelAndView("/adminlotteryresult/adminupanddownloadlotteryresult","sucess",sucess);
	}
	
	//download lotteryresult
	String path;
	@RequestMapping(value="/admindownloadlotteryresult.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView downloadExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String path=request.getServletContext().getRealPath("/upload");	
		File myfile=new File(path);
		if (!myfile.exists() && !myfile.isDirectory())      
		{         
		    myfile.mkdir();    
		} 
		File target=new File(path,"/lottery");
		ArrayList<LotteryResultPojo> al = adminShowAllLotteryResultService.exportAllLotteryResult();
		ServletOutputStream out;
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(target);
			WritableSheet sheet = book.createSheet("sheet0", 0);
			WritableFont font1 = new WritableFont(WritableFont.createFont("sans-serif"), 10, WritableFont.BOLD);
			font1.setColour(Colour.BLUE);
			WritableCellFormat format1 = new WritableCellFormat(font1);
			format1.setAlignment(jxl.format.Alignment.CENTRE);
			format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			WritableFont font2 = new WritableFont(WritableFont.createFont("sans-serif"), 10, WritableFont.BOLD);
			font2.setColour(Colour.BLACK);
			WritableCellFormat format2 = new WritableCellFormat(font2);
			format2.setAlignment(jxl.format.Alignment.CENTRE);
			format2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			Label firstlabel0 = new Label(0, 0, "Phase", format1);
			Label firstlabel1 = new Label(1, 0, "Date", format1);
			Label firstlabel2 = new Label(2, 0, "Red1", format1);
			Label firstlabel3 = new Label(3, 0, "Red2", format1);
			Label firstlabel4 = new Label(4, 0, "Red3", format1);
			Label firstlabel5 = new Label(5, 0, "Red4", format1);
			Label firstlabel6 = new Label(6, 0, "Red5", format1);
			Label firstlabel7 = new Label(7, 0, "Blue1", format1);
			Label firstlabel8 = new Label(8, 0, "Blue2", format1);
			
			sheet.addCell(firstlabel0);
			sheet.addCell(firstlabel1);
			sheet.addCell(firstlabel2);
			sheet.addCell(firstlabel3);
			sheet.addCell(firstlabel4);
			sheet.addCell(firstlabel5);
			sheet.addCell(firstlabel6);
			sheet.addCell(firstlabel7);
			sheet.addCell(firstlabel8);
			for (int i = 0; i < al.size(); i++) {
				LotteryResultPojo lotteryresultpojo = al.get(i);
				Number number1 = new Number(0, i + 1, lotteryresultpojo.getPhase(), format2);
				Label label1 = new Label(1, i + 1, lotteryresultpojo.getDate(), format2);
				Number number2 = new Number(2, i + 1, lotteryresultpojo.getRed1(), format2);
				Number number3 = new Number(3, i + 1, lotteryresultpojo.getRed2(), format2);
				Number number4 = new Number(4, i + 1, lotteryresultpojo.getRed3(), format2);
				Number number5 = new Number(5, i + 1, lotteryresultpojo.getRed4(), format2);
				Number number6 = new Number(6, i + 1, lotteryresultpojo.getRed5(), format2);
				Number number7 = new Number(7, i + 1, lotteryresultpojo.getBlue1(), format2);
				Number number8 = new Number(8, i + 1, lotteryresultpojo.getBlue2(), format2);
				
				sheet.addCell(number1);
				sheet.addCell(label1);
				sheet.addCell(number2);
				sheet.addCell(number3);
				sheet.addCell(number4);
				sheet.addCell(number5);
				sheet.addCell(number6);
				sheet.addCell(number7);
				sheet.addCell(number8);
			}
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FileInputStream in = new FileInputStream(target);
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName="+"lotteryresult.xls");
			out = response.getOutputStream();
			int b = 0;
			byte[] buffer = new byte[1024];
			while (b != -1) {
				b = in.read(buffer);
				out.write(buffer, 0, b);
			}
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return new ModelAndView("/adminlotteryresult/adminupanddownloadlotteryresult");
	}	
	
    //lotteryresultchart
	private JFreeChart chart;
    public JFreeChart getChart() {
        return chart;
    }

    public void setChart(JFreeChart chart) {
        this.chart = chart;
    }
    
    @RequestMapping(value="/showlotteryresultchart.do", method={RequestMethod.POST,RequestMethod.GET})
    public void showLotteryResultChart(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{
    	String phase1 = request.getParameter("startdate");
		String phase2 = request.getParameter("enddate");
		String type=request.getParameter("type");
		System.out.println(phase1);
		System.out.println(type);
		String paras[]={phase1,phase2};
		Map<String,Integer> lotterymap = new LinkedHashMap<String,Integer>();
    	int redone1=0,redone2=0,redone3=0,redone4=0,redone5=0,redone6=0,redone7=0,redone8=0,redone9=0,redone10=0,redone11=0,redone12=0,redone13=0,redone14=0,redone15=0,redone16=0,redone17=0,redone18=0,redone19=0,redone20=0,redone21=0,redone22=0,redone23=0,redone24=0,redone25=0,redone26=0,redone27=0,redone28=0,redone29=0,redone30=0,redone31=0,redone32=0,redone33=0,redone34=0,redone35=0,redoneothers=0;
 		int quantityone[]={redone1,redone2,redone3,redone4,redone5,redone6,redone7,redone8,redone9,redone10,redone11,redone12,redone13,redone14,redone15,redone16,redone17,redone18,redone19,redone20,redone21,redone22,redone23,redone24,redone25,redone26,redone27,redone28,redone29,redone30,redone31,redone32,redone33,redone34,redone35,redoneothers};
 		
 		quantityone=adminShowAllLotteryResultService.showLotteryResultChart(paras,type);
 		
 		for(int i=0;i<quantityone.length;i++){
 			lotterymap.put(i+1+"", quantityone[i]);
 		}
 		// 调用GSON jar工具包封装好的toJson方法，可直接生成JSON字符串
		Gson gson = new Gson();
		String json = gson.toJson(lotterymap);
		// 输出到界面
		System.out.println(json);
		response.setContentType("text/plain");
		response.setCharacterEncoding("gb2312");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = new PrintWriter(response.getOutputStream());
		out.print(json);
		out.flush();   
     }
}
