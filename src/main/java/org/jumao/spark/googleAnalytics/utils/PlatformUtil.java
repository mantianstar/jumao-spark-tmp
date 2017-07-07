package org.jumao.spark.googleAnalytics.utils;

import java.util.HashMap;
import java.util.Map;

public class PlatformUtil {

	public static final Map<String, String> platformIdViewIdMap = new HashMap<String, String>();

	public static final String Jumore_Master = "100101";
	public static final String Chemical_Industry = "100201";
	public static final String Coloured = "100301";
	public static final String Etrans_More = "100401";
	public static final String Coal = "100701";
	public static final String Steel = "100801";
	public static final String Mineral = "100901";
	public static final String Agriculture = "101001";
	public static final String Industrial = "101101";
	public static final String Consumable = "101201";
	public static final String Mechanics = "101301";
	public static final String Food = "101401";
	public static final String Finance = "101501";
	public static final String Bigdata = "101601";
	public static final String Certification = "101701";
	public static final String Tech = "101801";
	public static final String Consultancy = "101901";
	public static final String Oil = "102201";
    
    static {
//		platformIdViewIdMap.put("100001", "认证中心");
		platformIdViewIdMap.put(Jumore_Master, "140501227");//聚贸总站
		platformIdViewIdMap.put(Chemical_Industry, "140499722");
		platformIdViewIdMap.put(Coloured, "140514365");//JUMORENon-ferrous 有色
		platformIdViewIdMap.put(Etrans_More, "140582289");//EtransMore 聚运通
//		platformIdViewIdMap.put("100501", "企业中心");
//		platformIdViewIdMap.put("100601", "企业服务中心");
		platformIdViewIdMap.put(Coal, "140501028");
		platformIdViewIdMap.put(Steel, "140499723");//钢铁
		platformIdViewIdMap.put(Mineral, "140503029");//矿产
		platformIdViewIdMap.put(Agriculture, "140502429");
		platformIdViewIdMap.put(Industrial, "140494029");//工业品
		platformIdViewIdMap.put(Consumable, "140513767");//JUMORECG 消费品
		platformIdViewIdMap.put(Mechanics, "140515549");//机械
		platformIdViewIdMap.put(Food, "140494530");//食品
		platformIdViewIdMap.put(Finance, "140600419");//JUMOREFinance 金融
		platformIdViewIdMap.put(Bigdata, "140577827");
		platformIdViewIdMap.put(Certification, "140574653");
		platformIdViewIdMap.put(Tech, "140603028");//JUMORETech 聚智能
		platformIdViewIdMap.put(Consultancy, "140587227");//JUMOREConsultancy 聚咨询
//		platformIdViewIdMap.put("102001", "聚达通");
//		platformIdViewIdMap.put("102101", "支付结算中心");
		platformIdViewIdMap.put(Oil, "140486925");//JUMOREPetro  石油
    }

	public static String get(String key){
    	return platformIdViewIdMap.get(key);
    }
}
