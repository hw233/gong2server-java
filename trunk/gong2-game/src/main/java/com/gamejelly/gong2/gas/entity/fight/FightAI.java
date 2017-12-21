
package com.gamejelly.gong2.gas.entity.fight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.utils.GongUtils;

public class FightAI {

	public static List<Fighter> getFabaoTarget(FightContext fc, List<Fighter> allO, List<Fighter> allT, Fighter caster,
			FightFabao selectedFb) {
		LMap fbTemplate = selectedFb.getFbTemplate();
		List<Fighter> fs;
		int tgtEffect = fbTemplate.getInt("tgtEffect");
		fs = tgtEffect == 0 ? allT : allO;
		List<Fighter> copyFs = new ArrayList<Fighter>(fs);
		copyFs = rejectDeadTarget(copyFs);

		int rangeKind = selectedFb.getTemplateRangeKind();
		int count = selectedFb.getTemplateTgtNum();
		List<Fighter> ret = null;
		if (rangeKind == SysConstData.data.getInt("RANGE_KIND_1")) {
			// 单体
			ret = rangeKind1(copyFs, caster);
		} else if (rangeKind == SysConstData.data.getInt("RANGE_KIND_2")) {
			// 直线
			ret = rangeKind2(copyFs, caster);
		} else if (rangeKind == SysConstData.data.getInt("RANGE_KIND_3")) {
			// 前排
			ret = rangeKind3(copyFs, caster);
		} else if (rangeKind == SysConstData.data.getInt("RANGE_KIND_4")) {
			// 后排
			ret = rangeKind4(copyFs, caster);
		} else if (rangeKind == SysConstData.data.getInt("RANGE_KIND_5")) {
			// 随机
			ret = GongUtils.getRandomsFromList(copyFs, count, true);
		}

		if (ret == null) {
			ret = Collections.emptyList();
		}

		return ret;
	}

	private static List<Fighter> rejectDeadTarget(List<Fighter> fs) {
		if (CollectionUtils.isEmpty(fs)) {
			return fs;
		}
		Iterator<Fighter> fsIter = fs.iterator();
		while (fsIter.hasNext()) {
			Fighter f = fsIter.next();
			if (f.isDead()) {
				fsIter.remove();
			}
		}
		return fs;
	}

	private static List<Fighter> getAliveList(List<Fighter> lst) {
		List<Fighter> ret = new ArrayList<Fighter>();
		for (Fighter fighter : lst) {
			if (fighter.isAlive()) {
				ret.add(fighter);
			}
		}
		return ret;
	}

	private static List<Fighter> getDeadList(List<Fighter> lst) {
		List<Fighter> ret = new ArrayList<Fighter>();
		for (Fighter fighter : lst) {
			if (fighter.isDead()) {
				ret.add(fighter);
			}
		}
		return ret;
	}

	private static List<Fighter> getListByCount(List<Fighter> lst, int count) {
		List<Fighter> lstRet = new ArrayList<Fighter>();
		for (Fighter fighter : lst) {
			count--;
			if (count < 0) {
				break;
			}
			lstRet.add(fighter);
		}

		return lstRet;
	}

	/**
	 * 
	 * @param lst
	 * @param smallFront
	 *            true:从小到大 false:从大到小
	 */
	private static void sortList(List<Fighter> lst, final Boolean smallFront) {
		Collections.sort(lst, new Comparator<Fighter>() {
			@Override
			public int compare(Fighter a, Fighter b) {
				if (smallFront) {
					int offsetHpPer = (int) (a.getFinalHpPer() * 100) - (int) (b.getFinalHpPer() * 100);
					if (offsetHpPer == 0) {
						int random = (int) (Math.random() * 3);
						if (random == 0) {
							return 1;
						} else {
							return -1;
						}
					} else {
						return offsetHpPer;
					}
				} else {
					int offsetHpPer = (int) (b.getFinalHpPer() * 100) - (int) (a.getFinalHpPer() * 100);
					if (offsetHpPer == 0) {
						int random = (int) (Math.random() * 3);
						if (random == 0) {
							return 1;
						} else {
							return -1;
						}
					} else {
						return offsetHpPer;
					}

				}
			}
		});
	}

	/***
	 * 选择距离最小的目标
	 */
	public static Fighter selectMinDistanceTarget(List<Fighter> lstTarget, final Fighter roundFighter) {
		if (CollectionUtils.isEmpty(lstTarget)) {
			return null;
		}

		Fighter ret = null;
		int distanceToTarget = 0;
		for (int i = 0; i < lstTarget.size(); i++) {
			int curDistance = Math.abs(roundFighter.getCol() - lstTarget.get(i).getCol()) + lstTarget.get(i).getRow();
			if (i == 0) {
				ret = lstTarget.get(i);
				distanceToTarget = Math.abs(roundFighter.getCol() - ret.getCol()) + ret.getRow();
			} else {
				if (curDistance < distanceToTarget) {
					ret = lstTarget.get(i);
					distanceToTarget = Math.abs(roundFighter.getCol() - ret.getCol()) + ret.getRow();
				}
			}
		}

		return ret;
	}

	// 单体距离最近的
	private static List<Fighter> rangeKind1(List<Fighter> copyFs, Fighter roundFighter) {
		Fighter minDisFighter = selectMinDistanceTarget(copyFs, roundFighter);
		if (minDisFighter != null) {
			return Arrays.asList(minDisFighter);
		}
		return new ArrayList<Fighter>();
	}

	// 直线
	private static List<Fighter> rangeKind2(List<Fighter> copyFs, Fighter roundFighter) {
		Fighter minDisFighter = selectMinDistanceTarget(copyFs, roundFighter);
		if (minDisFighter != null) {
			final int curCol = minDisFighter.getCol();
			@SuppressWarnings("unchecked")
			List<Fighter> retList = (List<Fighter>) CollectionUtils.select(copyFs, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					return ((Fighter) object).getCol() == curCol;
				}
			});

			if (CollectionUtils.isNotEmpty(retList)) {
				return retList;
			}
		}
		return new ArrayList<Fighter>();
	}

	// 前排
	private static List<Fighter> rangeKind3(List<Fighter> copyFs, Fighter roundFighter) {
		final int frontRowFlag = 1;

		// 判断目标前排有没有人
		@SuppressWarnings("unchecked")
		List<Fighter> frontTargetList = (List<Fighter>) CollectionUtils.select(copyFs, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				return ((Fighter) object).getRow() == frontRowFlag;
			}
		});

		if (CollectionUtils.isNotEmpty(frontTargetList)) {
			return frontTargetList;
		} else {
			return copyFs;
		}
	}

	// 后排
	private static List<Fighter> rangeKind4(List<Fighter> copyFs, Fighter roundFighter) {

		final int backRowFlag = 2;

		// 判断目标后排有没有人
		@SuppressWarnings("unchecked")
		List<Fighter> backTargetList = (List<Fighter>) CollectionUtils.select(copyFs, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				return ((Fighter) object).getRow() == backRowFlag;
			}
		});

		if (CollectionUtils.isNotEmpty(backTargetList)) {
			return backTargetList;
		} else {
			return copyFs;
		}
	}

}
