/**
 *
 */

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main{
	
	
public Main() throws SQLException, SAXException, IOException, ParserConfigurationException {
		super();
		run();
	}

public static void main(String[] args){
	try {
		try {
			new Main();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
//	ExtractXML xml = new ExtractXML();
//	try {
//		xml.extact("F:\\EclipseWorkspace\\AnsproWorkspace\\Cricket_Analysis\\Dataset\\cricsheet-xml-master\\data\\ODI\\1001371.xml");
//	} catch (SAXException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (ParserConfigurationException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
}

public void run() throws SAXException, IOException, ParserConfigurationException, SQLException{
	String path = System.getProperty("user.dir");
	path = path+"//"+Constants.datasetName+"//cricsheet-xml-master//data//ODI";
	File f = new File(path);
	File[] f_files = f.listFiles();
	long startTime = System.currentTimeMillis();
	int count = 0;
	for(File F:f_files){
		System.out.println("count= "+count);
		String absPath = F.getAbsolutePath();
		String fileName = F.getName();
		System.out.println("FileName="+fileName);
		System.out.println("Path= "+absPath);
		ExtractXML extract = new ExtractXML();
		extract.extact(absPath);
		Info infoObj = extract.getInfoObj();
		Innings inningsObj = extract.getInningsObj();
//		System.out.println(infoObj.toString());
//		System.out.println(inningsObj.toString());
		
		try{
			String city = infoObj.getCity();
			String winner = infoObj.getWinner();
			String player_of_the_match = infoObj.getPlayerOfMatch();
			String venue = infoObj.getVenue();
			ArrayList<String> teams = infoObj.getTeams();
			
			String team = "";
			String over = "";
			String ball = "";
			String batsman = "";
			String bowler = "";
			String non_striker = "";
			String run_scored = "";
			String player_out = "no data";
			String kind = "no data";
			String fielder = "no data";
			
			ArrayList<Delivery> deliveryarr = inningsObj.getDelivery();
			for(Delivery D:deliveryarr){
				team = D.getTeamName();
				over = D.getOver()+"";
				ball = D.getBall()+"";
				batsman = D.getBatsman();
				bowler = D.getBowler();
				non_striker = D.getNon_striker();
				run_scored = D.getRuns().get("total");
				if(!D.getWickets().isEmpty()){
					player_out = D.getWickets().get(Constants.player_out);
					kind = D.getWickets().get(Constants.kind);
					if(D.getWickets().containsKey(Constants.fielder)){
						fielder = D.getWickets().get(Constants.fielder);
					}
				}
				System.out.println("team= "+team);
				System.out.println("Batsman= "+batsman);
				System.out.println("Bowler= "+bowler);
				String[] arr = {city,winner,player_of_the_match,venue,teams.toString(),team,over,ball,batsman,bowler,non_striker,run_scored,player_out,kind,fielder};
				String table_name = "match_analysis";
			}
			System.out.println("sucessfully inserted");
			}catch(Exception e){
				e.printStackTrace();
				break;
			}
		count++;
	}
	long endTime  = System.currentTimeMillis();
	long tcons = endTime-startTime;
	System.out.println("Time Consumed= "+tcons/360+" sec");
System.out.println("Done");
}

}
