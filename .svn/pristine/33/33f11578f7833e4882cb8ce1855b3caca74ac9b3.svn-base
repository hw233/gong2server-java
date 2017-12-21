package com.gamejelly.gong2.gas.service.gm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.gas.service.user.ItemService;
import com.gamejelly.gong2.gas.service.user.ServantService;
import com.gamejelly.gong2.utils.GongLogConstants;
import com.gamejelly.gong2.utils.GongRpcConstants;
import com.gamejelly.gong2.vo.AvatarVO;
import com.hadoit.game.engine.guardian.gas.GasManager;

@Component("gmService")
public class GmService {

	@Autowired
	@Qualifier("gasManager")
	private GasManager gasManager;

	@Autowired
	@Qualifier("itemService")
	private ItemService itemService;

	@Autowired
	@Qualifier("servantService")
	ServantService servantService;

	/**
	 * @Title: addItem @Description: TODO(增加物品gm命令) @param @param
	 *         entity @param @param templateId @param @param count 设定文件 @return
	 *         void 返回类型 @throws
	 */
	public void addItem(AvatarEntity entity, int templateId, int count) {
		boolean result = itemService.notifyItemChanges(entity,
				itemService.addItem(entity, templateId, count, GongLogConstants.LOG_ITEM_CHANGE_GM_ADD, 0, 0));
		if (result) {
			entity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(entity));
		}
	}

	/**
	 * @Title: addExpForce @Description: TODO(增加经验gm命令) @param @param
	 *         entity @param @param incr 设定文件 @return void 返回类型 @throws
	 */
	public void addExpForce(AvatarEntity entity, int incr) {
		entity.addExp(incr, GongLogConstants.LOG_EXP_CHANGE_GM);
		entity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(entity));
	}

	/**
	 * @Title: addMoneyForce @Description: TODO(增加银两gm命令) @param @param
	 *         entity @param @param incr 设定文件 @return void 返回类型 @throws
	 */
	public void addMoneyForce(AvatarEntity entity, long incr) {
		entity.getAvatarModel().incrMoney(incr);
		entity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(entity));
	}

	/**
	 * @Title: addGoldForce @Description: TODO(增加元宝gm命令) @param @param
	 *         entity @param @param incr 设定文件 @return void 返回类型 @throws
	 */
	public void addGoldForce(AvatarEntity entity, long incr) {
		entity.getAvatarModel().incrFreeGold(incr);
		entity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(entity));
	}

	public void addServant(AvatarEntity entity, int templateId, int pos) {
		servantService.addServant(entity, templateId, pos);
	}

}
