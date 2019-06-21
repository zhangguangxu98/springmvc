package com.springmvc.controller;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.solidfire.gson.Gson;
import com.springmvc.page.Pager;
import com.springmvc.pojo.MyLogPojo;
import com.springmvc.pojo.MyPlanPojo;
import com.springmvc.service.AdminShowAllMyPlanService;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller("AdminShowAllMyPlanController")
public class AdminShowAllMyPlanController {
	
	@Resource(name="adminShowAllMyPlanService") 
	private AdminShowAllMyPlanService adminShowAllMyPlanService;
	
    //添加日志
	@RequestMapping(value="/adminaddmyplan.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminAddMyLog(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String startdate = request.getParameter("startdate");
        String enddate = request.getParameter("enddate");
        String plan = request.getParameter("plan");
        String content = request.getParameter("content");
        String assessment = request.getParameter("assessment");
        
		Object object[]={id,startdate,enddate,plan,content,assessment};

		for(int i=0;i<object.length;i++){
			if("".equals(object[i])){
				object[i]=0;
			}
		}
		String sucess=adminShowAllMyPlanService.addMyPlan(object);
		return new ModelAndView("redirect:/adminshowallmyplan.do?pageNum=1");
	}
	
	//删除日志
	@RequestMapping(value="/admindeletemyplan.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminDeleteMyLog(HttpServletRequest request,HttpServletResponse response){
		String[] phase = request.getParameterValues("tduCheckBox");

		if(null==phase||"".equals(phase)){
			return new ModelAndView("redirect:/adminshowallmyplan.do?pageNum=1");
		}
		for(int i=0;i<phase.length;i++){
			String paras[]={phase[i]};
			System.out.println(phase[i]);
			adminShowAllMyPlanService.deleteMyPlan(paras);
		}
		return new ModelAndView("redirect:/adminshowallmyplan.do?pageNum=1");
	}
		
	//显示日志
	@RequestMapping(value="/adminshowallmyplan.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminShowAllMyLog(HttpServletRequest request,HttpServletResponse response){
		String pageNumString = request.getParameter("pageNum");
		String phase1 = request.getParameter("startdate");
		String phase2 = request.getParameter("enddate");
		System.out.println("phase1"+phase1);
		int pageNum=Integer.valueOf(pageNumString);
		String paras[]={phase1,phase2};
		final int pageSize=10;
		
		MyPlanPojo myPlanPojoPojo=new MyPlanPojo();
		Pager pager=new Pager();
		
		pager = adminShowAllMyPlanService.searchPager(myPlanPojoPojo, pageNum, pageSize,paras);
		return new ModelAndView("/adminmyplan/adminshowallmyplan","pager",pager);
	}
	
	//更新日志
	@RequestMapping(value="/adminbeforeupdatemyplan.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView beforeUpdateMyPlan(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String paras[]={id};
		
		ArrayList<MyPlanPojo> adminbeforeupdatemyplanlist=adminShowAllMyPlanService.beforeUpdateMyPlan(paras);
		return new ModelAndView("/adminmyplan/adminbeforeupdatemyplan","adminbeforeupdatemyplanlist",adminbeforeupdatemyplanlist);
	}
	@RequestMapping(value="/adminupdatemyplan.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView updateMyPlan(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
        String plan = request.getParameter("plan");
        String content = request.getParameter("content");
        String assessment = request.getParameter("assessment");
        
		String paras[]={startdate,enddate,plan,content,assessment,id};
		String sucess=adminShowAllMyPlanService.updateMyPlan(paras);
		return new ModelAndView("redirect:/adminbeforeupdatemyplan.do?id="+id);
	}
	
	//上传文件
	@RequestMapping(value="/adminuploadmyplan.do", method={RequestMethod.POST,RequestMethod.GET})
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
			return new ModelAndView("/adminmyplan/adminupanddownloadmyplan","error",error);
		}	
		String sucess=adminShowAllMyPlanService.readExcel(uploadPath+"/"+myFileFileName);
		return new ModelAndView("/adminmyplan/adminupanddownloadmyplan","sucess",sucess);
	}
	
	//下载文件
	String path;
	@RequestMapping(value="/admindownloadmyplan.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView downloadExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String phase1 = request.getParameter("startdate");
		String phase2 = request.getParameter("enddate");
		String paras[]={phase1,phase2};
		String path=request.getServletContext().getRealPath("/upload");	
		File myfile=new File(path);
		if (!myfile.exists() && !myfile.isDirectory())      
		{         
		    myfile.mkdir();    
		} 
		File target=new File(path,"/myplan");
		ArrayList<MyPlanPojo> al = adminShowAllMyPlanService.exportAllMyPlan(paras);
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
			String[] firstline={"序号","起始日期","结束日期","计划主题","计划内容","评价"};
			Label[] firstlabel=new Label[6];
			for(int i=0;i<firstline.length;i++){
				firstlabel[i]=new Label(i,0,firstline[i],format1);
				sheet.addCell(firstlabel[i]);
			}

			for (int i = 0; i < al.size(); i++) {
				MyPlanPojo myplanpojo = al.get(i);
				Number[] number=new Number[1];
				Label[] label=new Label[5];
				number[0] = new Number(0, i + 1, myplanpojo.getId(), format2);
				label[0] = new Label(1, i + 1, myplanpojo.getStartdate(), format2);
				label[1] = new Label(2, i + 1, myplanpojo.getEnddate(), format2);
				label[2] = new Label(3, i + 1, myplanpojo.getPlan(), format2);
				label[3] = new Label(4, i + 1, myplanpojo.getContent(), format2);
				label[4] = new Label(5, i + 1, myplanpojo.getAssessment(), format2);
				for(int j=0;j<number.length;j++){
					sheet.addCell(number[j]);
				}
				for(int k=0;k<label.length;k++){
					sheet.addCell(label[k]);
				}
			}
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FileInputStream in = new FileInputStream(target);
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName="+"myplan"+phase1+"至"+phase2+".xls");
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
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return new ModelAndView("/adminmyplan/adminupanddownloadmyplan");
	}
	
	//生成报表
	@RequestMapping(value="/showmyplanchart.do", method={RequestMethod.POST,RequestMethod.GET})
    public void showMyPlanChart(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{
    	String phase1 = request.getParameter("startdate");
		String phase2 = request.getParameter("enddate");
		String type=request.getParameter("type");
		System.out.println(phase1);
		System.out.println(type);
		String paras[]={phase1,phase2};
		
    	Map<String,Integer> planmap = new LinkedHashMap<String,Integer>();
    	planmap=adminShowAllMyPlanService.showMyPlanChart(paras);
    	// 调用GSON jar工具包封装好的toJson方法，可直接生成JSON字符串
		Gson gson = new Gson();
		String json = gson.toJson(planmap);
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
