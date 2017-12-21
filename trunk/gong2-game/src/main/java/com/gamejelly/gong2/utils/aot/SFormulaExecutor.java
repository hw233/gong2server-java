package com.gamejelly.gong2.utils.aot;

import java.util.Map;
import com.gamejelly.gong2.gas.entity.fight.BaseFightProperty;
import com.gamejelly.gong2.gas.entity.ServantEntity;
import com.gamejelly.gong2.gas.entity.MonsterEntity;
public class SFormulaExecutor {

	static class math{
		static double min(Number a, Number b){
			return Math.min(a.doubleValue(), b.doubleValue());
		}
		
		static double rint(Number a){
			return Math.rint(a.doubleValue());
		}
		
		static double max(Number a, Number b){
			return Math.max(a.doubleValue(), b.doubleValue());
		}
		
		static double floor(Number a){
			return Math.floor(a.doubleValue());
		}
		
		static double random(){
			return Math.random();
		}
	}
	
	static int parseInt(Number a) {
		return a.intValue();
	}
	static BaseFightProperty bfp(Map<String, Object> params, String p){
		return (BaseFightProperty)params.get(p);
	}
	
	static double d(Map<String, Object> params, String p){
		return Double.valueOf(params.get(p).toString());
	}			

	private static Object finalCriRate(BaseFightProperty t, BaseFightProperty o){		return o.totCri+o.bufCri-t.totDcri-t.bufDcri;	}
	private static Object finalParryRate(BaseFightProperty t, BaseFightProperty o){		return o.totParry+o.bufParry-t.totDparry-t.bufDparry;	}
	private static Object totalZl(BaseFightProperty o){		return math.rint(o.disAtk*29.3+o.disDef*21.1+o.disDpower*21.1+o.disHp*1+o.disCri*4085+o.disDcri*4085+o.disFocus*817+o.disDparry*2458.3+o.disParry*2458.3+o.disTen*2458.3+o.disPen*4219.4+o.disDeHIT*26.3+o.disEahit*26.3+o.disPreDeepen*8169.9+o.disPreEase*8169.9);	}
	private static Object PhyInjury(BaseFightProperty t, BaseFightProperty o){		return ((math.max(o.finalAtk-t.finalDef*(1-o.finalPen),0)+o.finalAtk*0.11)*o.skillDamegeProb+o.skillDamegeAdd+o.finalDeHIT-t.finalEahit)*(1+o.finalPreDeepen-t.finalPreEase);	}
	private static Object MagInjury(BaseFightProperty t, BaseFightProperty o){		return ((math.max(o.finalAtk-t.finalDpower*(1-o.finalPen),0)+o.finalAtk*0.11)*o.skillDamegeProb+o.skillDamegeAdd+o.finalDeHIT-t.finalEahit)*(1+o.finalPreDeepen-t.finalPreEase);	}
	private static Object criPhyInjury(BaseFightProperty t, BaseFightProperty o){		return ((math.max(o.finalAtk-t.finalDef*(1-o.finalPen),0)+o.finalAtk*0.11)*o.skillDamegeProb+o.skillDamegeAdd+o.finalDeHIT-t.finalEahit)*(1+o.finalPreDeepen-t.finalPreEase+o.criProb);	}
	private static Object criMagInjury(BaseFightProperty t, BaseFightProperty o){		return ((math.max(o.finalAtk-t.finalDpower*(1-o.finalPen),0)+o.finalAtk*0.11)*o.skillDamegeProb+o.skillDamegeAdd+o.finalDeHIT-t.finalEahit)*(1+o.finalPreDeepen-t.finalPreEase+o.criProb);	}
	private static Object parryPhyInjury(BaseFightProperty t, BaseFightProperty o){		return ((math.max(o.finalAtk-t.finalDef*(1-o.finalPen),0)+o.finalAtk*0.11)*o.skillDamegeProb+o.skillDamegeAdd+o.finalDeHIT-t.finalEahit)*(1+o.finalPreDeepen-t.finalPreEase-t.parryProb);	}
	private static Object parryMagInjury(BaseFightProperty t, BaseFightProperty o){		return ((math.max(o.finalAtk-t.finalDpower*(1-o.finalPen),0)+o.finalAtk*0.11)*o.skillDamegeProb+o.skillDamegeAdd+o.finalDeHIT-t.finalEahit)*(1+o.finalPreDeepen-t.finalPreEase-t.parryProb);	}
	private static Object calcMonsterLv(BaseFightProperty o){		return o.getLv();	}

	public static Object calcSFormula(int fId, Map<String, Object> params) { 
		switch (fId) { 
			case 99001: 
				BaseFightProperty t_99001 = bfp(params, "t"); 
				BaseFightProperty o_99001 = bfp(params, "o"); 
				return finalCriRate(t_99001,o_99001); 
			case 99002: 
				BaseFightProperty t_99002 = bfp(params, "t"); 
				BaseFightProperty o_99002 = bfp(params, "o"); 
				return finalParryRate(t_99002,o_99002); 
			case 99014: 
				BaseFightProperty o_99014 = bfp(params, "o"); 
				return totalZl(o_99014); 
			case 99015: 
				BaseFightProperty t_99015 = bfp(params, "t"); 
				BaseFightProperty o_99015 = bfp(params, "o"); 
				return PhyInjury(t_99015,o_99015); 
			case 99016: 
				BaseFightProperty t_99016 = bfp(params, "t"); 
				BaseFightProperty o_99016 = bfp(params, "o"); 
				return MagInjury(t_99016,o_99016); 
			case 99017: 
				BaseFightProperty t_99017 = bfp(params, "t"); 
				BaseFightProperty o_99017 = bfp(params, "o"); 
				return criPhyInjury(t_99017,o_99017); 
			case 99018: 
				BaseFightProperty t_99018 = bfp(params, "t"); 
				BaseFightProperty o_99018 = bfp(params, "o"); 
				return criMagInjury(t_99018,o_99018); 
			case 99019: 
				BaseFightProperty t_99019 = bfp(params, "t"); 
				BaseFightProperty o_99019 = bfp(params, "o"); 
				return parryPhyInjury(t_99019,o_99019); 
			case 99020: 
				BaseFightProperty t_99020 = bfp(params, "t"); 
				BaseFightProperty o_99020 = bfp(params, "o"); 
				return parryMagInjury(t_99020,o_99020); 
			case 99301: 
				BaseFightProperty o_99301 = bfp(params, "o"); 
				return calcMonsterLv(o_99301); 
			default: 
			break; 
		} 
		return 0; 
	} 

}