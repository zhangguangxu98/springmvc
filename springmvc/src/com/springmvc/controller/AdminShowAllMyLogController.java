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

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedAreaRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
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
import com.springmvc.pojo.MyLogPojo;
import com.springmvc.service.AdminShowAllLotteryResultService;
import com.springmvc.service.AdminShowAllMyLogService;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller("AdminShowAllMyLogController")
public class AdminShowAllMyLogController {
	
	@Resource(name="adminShowAllMyLogService") 
	private AdminShowAllMyLogService adminShowAllMyLogService;
	
    //添加日志
	@RequestMapping(value="/adminaddmylog.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminAddMyLog(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String date = request.getParameter("date");
        String home = request.getParameter("home");
        String clothes = request.getParameter("clothes");
        String meal = request.getParameter("meal");
        String room = request.getParameter("room");
        String trip = request.getParameter("trip");
        String lifeuse = request.getParameter("lifeuse");
        String play = request.getParameter("play");
        String insurance = request.getParameter("insurance");
        String idroutine = request.getParameter("idroutine");
		String dateroutine = request.getParameter("dateroutine");
        String homeroutine = request.getParameter("homeroutine");
        String clothesroutine = request.getParameter("clothesroutine");
        String mealroutine = request.getParameter("mealroutine");
        String roomroutine = request.getParameter("roomroutine");
        String triproutine = request.getParameter("triproutine");
        String useroutine = request.getParameter("useroutine");
        String playroutine = request.getParameter("playroutine");
        String insuranceroutine = request.getParameter("insuranceroutine");
        String selfcontrol = request.getParameter("selfcontrol");
		String diligence = request.getParameter("diligence");
        String goodorder = request.getParameter("goodorder");
        String clean = request.getParameter("clean");
        String frugality = request.getParameter("frugality");
        String honest = request.getParameter("honest");
        String integrity = request.getParameter("integrity");
        String modest = request.getParameter("modest");
        String friendly = request.getParameter("friendly");
        String tolerant = request.getParameter("tolerant");
        String diary = request.getParameter("diary");
		Object object[]={id,date,home,clothes,meal,room,trip,lifeuse,play,insurance,idroutine,dateroutine
				,homeroutine,clothesroutine,mealroutine,roomroutine,triproutine,useroutine,playroutine
				,insuranceroutine,selfcontrol,diligence,goodorder,clean,frugality,honest,integrity,modest
				,friendly,tolerant,diary};

		for(int i=0;i<object.length;i++){
			if("".equals(object[i])){
				object[i]=0;
			}
		}
		String sucess=adminShowAllMyLogService.addMyLog(object);
		return new ModelAndView("redirect:/adminshowallmylog.do?pageNum=1");
	}
	
	//删除日志
	@RequestMapping(value="/admindeletemylog.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminDeleteMyLog(HttpServletRequest request,HttpServletResponse response){
		String[] phase = request.getParameterValues("tduCheckBox");
		System.out.println("phase"+phase);
		if(null==phase||"".equals(phase)){
			return new ModelAndView("redirect:/adminshowallmylog.do?pageNum=1");
		}
		for(int i=0;i<phase.length;i++){
			String paras[]={phase[i]};
			System.out.println(phase[i]);
			adminShowAllMyLogService.deleteMyLog(paras);
		}
		return new ModelAndView("redirect:/adminshowallmylog.do?pageNum=1");
	}
		
	//显示日志
	@RequestMapping(value="/adminshowallmylog.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminShowAllMyLog(HttpServletRequest request,HttpServletResponse response){
		String pageNumString = request.getParameter("pageNum");
		String phase1 = request.getParameter("startdate");
		String phase2 = request.getParameter("enddate");
		System.out.println("phase1"+phase1);
		int pageNum=Integer.valueOf(pageNumString);
		String paras[]={phase1,phase2};
		final int pageSize=10;
		
		MyLogPojo myLogPojoPojo=new MyLogPojo();
		Pager pager=new Pager();
		
		pager = adminShowAllMyLogService.searchPager(myLogPojoPojo, pageNum, pageSize,paras);
		return new ModelAndView("/adminmylog/adminshowallmylog","pager",pager);
	}
	
	//更新日志
	@RequestMapping(value="/adminbeforeupdatemylog.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView beforeUpdateLotteryResult(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String paras[]={id};
		
		ArrayList<MyLogPojo> adminbeforeupdatemyloglist=adminShowAllMyLogService.beforeUpdateMyLog(paras);
		return new ModelAndView("/adminmylog/adminbeforeupdatemylog","adminbeforeupdatemyloglist",adminbeforeupdatemyloglist);
	}
	@RequestMapping(value="/adminupdatemylog.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView updateLotteryResult(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String date = request.getParameter("date");
        String home = request.getParameter("home");
        String clothes = request.getParameter("clothes");
        String meal = request.getParameter("meal");
        String room = request.getParameter("room");
        String trip = request.getParameter("trip");
        String use = request.getParameter("lifeuse");
        String play = request.getParameter("play");
        String insurance = request.getParameter("insurance");
        String idroutine = request.getParameter("idroutine");
		String dateroutine = request.getParameter("dateroutine");
        String homeroutine = request.getParameter("homeroutine");
        String clothesroutine = request.getParameter("clothesroutine");
        String mealroutine = request.getParameter("mealroutine");
        String roomroutine = request.getParameter("roomroutine");
        String triproutine = request.getParameter("triproutine");
        String useroutine = request.getParameter("useroutine");
        String playroutine = request.getParameter("playroutine");
        String insuranceroutine = request.getParameter("insuranceroutine");
        String selfcontrol = request.getParameter("selfcontrol");
		String diligence = request.getParameter("diligence");
        String order = request.getParameter("goodorder");
        String clean = request.getParameter("clean");
        String frugality = request.getParameter("frugality");
        String honest = request.getParameter("honest");
        String integrity = request.getParameter("integrity");
        String modest = request.getParameter("modest");
        String friendly = request.getParameter("friendly");
        String tolerant = request.getParameter("tolerant");
        String diary = request.getParameter("diary");
        
		String paras[]={date,home,clothes,meal,room,trip,use,play,insurance,idroutine,dateroutine
				,homeroutine,clothesroutine,mealroutine,roomroutine,triproutine,useroutine,playroutine
				,insuranceroutine,selfcontrol,diligence,order,clean,frugality,honest,integrity,modest
				,friendly,tolerant,diary,id};
		String sucess=adminShowAllMyLogService.updateMyLog(paras);
		return new ModelAndView("redirect:/adminbeforeupdatemylog.do?id="+id);
	}
	
	//上传文件
	@RequestMapping(value="/adminuploadmylog.do", method={RequestMethod.POST,RequestMethod.GET})
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
			return new ModelAndView("/adminmylog/adminupanddownloadmylog","error",error);
		}	
		String sucess=adminShowAllMyLogService.readExcel(uploadPath+"/"+myFileFileName);
		return new ModelAndView("/adminmylog/adminupanddownloadmylog","sucess",sucess);
	}
	
	//下载文件
	String path;
	@RequestMapping(value="/admindownloadmylog.do", method={RequestMethod.POST,RequestMethod.GET})
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
		File target=new File(path,"/lottery");
		ArrayList<MyLogPojo> al = adminShowAllMyLogService.exportAllMyLog(paras);
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
			String[] firstline={"序号","日期","家庭","衣服","食物","住宿","交通","用品","娱乐","收入","序号事件","日期事件",
					"家庭事件","衣服事件","食物事件","住宿事件","交通事件","用品事件","娱乐事件","收入事件","自制","勤奋",
					"秩序","整洁","节俭","诚实","正直","谦虚","友善","宽容","日记"};
			Label[] firstlabel=new Label[31];
			for(int i=0;i<firstline.length;i++){
				firstlabel[i]=new Label(i,0,firstline[i],format1);
				sheet.addCell(firstlabel[i]);
			}
			/*Label firstlabel0 = new Label(0, 0, "序号", format1);
			Label firstlabel1 = new Label(1, 0, "日期", format1);
			Label firstlabel2 = new Label(2, 0, "家庭", format1);
			Label firstlabel3 = new Label(3, 0, "衣服", format1);
			Label firstlabel4 = new Label(4, 0, "食物", format1);
			Label firstlabel5 = new Label(5, 0, "住宿", format1);
			Label firstlabel6 = new Label(6, 0, "交通", format1);
			Label firstlabel7 = new Label(7, 0, "用品", format1);
			Label firstlabel8 = new Label(8, 0, "娱乐", format1);
			Label firstlabel9 = new Label(9, 0, "社保", format1);
			Label firstlabel10 = new Label(10, 0, "序号事件", format1);
			Label firstlabel11 = new Label(11, 0, "日期事件", format1);
			Label firstlabel12 = new Label(12, 0, "家庭事件", format1);
			Label firstlabel13 = new Label(13, 0, "衣服事件", format1);
			Label firstlabel14 = new Label(14, 0, "食物事件", format1);
			Label firstlabel15 = new Label(15, 0, "住宿事件", format1);
			Label firstlabel16 = new Label(16, 0, "交通事件", format1);
			Label firstlabel17 = new Label(17, 0, "用品事件", format1);
			Label firstlabel18 = new Label(18, 0, "娱乐事件", format1);
			Label firstlabel19 = new Label(19, 0, "社保事件", format1);
			Label firstlabel20 = new Label(20, 0, "自制", format1);
			Label firstlabel21 = new Label(21, 0, "勤奋", format1);
			Label firstlabel22 = new Label(22, 0, "秩序", format1);
			Label firstlabel23 = new Label(23, 0, "整洁", format1);
			Label firstlabel24 = new Label(24, 0, "节俭", format1);
			Label firstlabel25 = new Label(25, 0, "诚实", format1);
			Label firstlabel26 = new Label(26, 0, "正直", format1);
			Label firstlabel27 = new Label(27, 0, "谦虚", format1);
			Label firstlabel28 = new Label(28, 0, "友善", format1);
			Label firstlabel29 = new Label(29, 0, "宽容", format1);
			Label firstlabel30 = new Label(30, 0, "日记", format1);*/
			
			for (int i = 0; i < al.size(); i++) {
				MyLogPojo mylogpojo = al.get(i);
				Number[] number=new Number[9];
				Label[] label=new Label[22];
				number[0] = new Number(0, i + 1, mylogpojo.getId(), format2);
				label[0] = new Label(1, i + 1, mylogpojo.getDate(), format2);
				number[1] = new Number(2, i + 1, mylogpojo.getHome(), format2);
				number[2] = new Number(3, i + 1, mylogpojo.getClothes(), format2);
				number[3] = new Number(4, i + 1, mylogpojo.getMeal(), format2);
				number[4] = new Number(5, i + 1, mylogpojo.getRoom(), format2);
				number[5] = new Number(6, i + 1, mylogpojo.getTrip(), format2);
				number[6] = new Number(7, i + 1, mylogpojo.getLifeuse(), format2);
				number[7] = new Number(8, i + 1, mylogpojo.getPlay(), format2);
				number[8] = new Number(9, i + 1, mylogpojo.getInsurance(), format2);
				label[1] = new Label(10, i + 1, mylogpojo.getIdroutine(), format2);
				label[2] = new Label(11, i + 1, mylogpojo.getDateroutine(), format2);
				label[3] = new Label(12, i + 1, mylogpojo.getHomeroutine(), format2);
				label[4] = new Label(13, i + 1, mylogpojo.getClothesroutine(), format2);
				label[5] = new Label(14, i + 1, mylogpojo.getMealroutine(), format2);
				label[6] = new Label(15, i + 1, mylogpojo.getRoomroutine(), format2);
				label[7] = new Label(16, i + 1, mylogpojo.getTriproutine(), format2);
				label[8] = new Label(17, i + 1, mylogpojo.getUseroutine(), format2);
				label[9] = new Label(18, i + 1, mylogpojo.getPlayroutine(), format2);
				label[10] = new Label(19, i + 1, mylogpojo.getInsuranceroutine(), format2);
				label[11] = new Label(20, i + 1, mylogpojo.getSelfcontrol(), format2);
				label[12] = new Label(21, i + 1, mylogpojo.getDiligence(), format2);
				label[13] = new Label(22, i + 1, mylogpojo.getGoodorder(), format2);
				label[14] = new Label(23, i + 1, mylogpojo.getClean(), format2);
				label[15] = new Label(24, i + 1, mylogpojo.getFrugality(), format2);
				label[16] = new Label(25, i + 1, mylogpojo.getHonest(), format2);
				label[17] = new Label(26, i + 1, mylogpojo.getIntegrity(), format2);
				label[18] = new Label(27, i + 1, mylogpojo.getModest(), format2);
				label[19] = new Label(28, i + 1, mylogpojo.getFriendly(), format2);
				label[20] = new Label(29, i + 1, mylogpojo.getTolerant(), format2);
				label[21] = new Label(30, i + 1, mylogpojo.getDiary(), format2);
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
			response.setHeader("Content-Disposition", "attachment;fileName="+"mylog"+phase1+"至"+phase2+".xls");
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
		return new ModelAndView("/adminmylog/adminupanddownloadmylog");
	}
	
	
	//生成报表
	@RequestMapping(value="/showmylogchart.do", method={RequestMethod.POST,RequestMethod.GET})
    public void showMyLogChart(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{
    	String phase1 = request.getParameter("startdate");
		String phase2 = request.getParameter("enddate");
		String paras[]={phase1,phase2};
		
    	int money[]=new int[9];
    	money=adminShowAllMyLogService.showMyLogChart(paras);
    	for(int i=0;i<money.length-2;i++){
    		money[money.length-1]+=money[i];
    	}
    	System.out.println(money[money.length-1]);
    	// 调用GSON jar工具包封装好的toJson方法，可直接生成JSON字符串
		Gson gson = new Gson();
		String json = gson.toJson(money);
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
