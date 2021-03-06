/**
* Copyright (c) 2015 Mustafa DUMLUPINAR, mdumlupinar@gmail.com
*
* This file is part of seyhan project.
*
* seyhan is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package data.transfer.ws2ws;

import java.util.HashSet;
import java.util.Set;

import models.SaleCampaign;
import models.SaleSeller;
import enums.Module;
/**
 * @author mdpinar
*/
class SaleTransfer extends BaseTransfer {

	@Override
	public void transferInfo(int sourceWS, int targetWS) {
		executeInsertQueryForInfoTables(new SaleSeller(""), sourceWS, targetWS);

		Set<String> rnmForCampaign = new HashSet<String>();
		for (int i = 0; i < 10; i++) {
			rnmForCampaign.add("extra_fields"+i);
		}
		rnmForCampaign.add("stock_category");

		Set<String> privateDeniedListForCampaign = new HashSet<String>();

		for (int i = 0; i < 10; i++) {
			privateDeniedListForCampaign.add("extra_fields");
		}

		privateDeniedListForCampaign.add("category");
		executeInsertQueryForInfoTables(new SaleCampaign(), sourceWS, targetWS, rnmForCampaign, privateDeniedListForCampaign);

		for (int i = 0; i < 10; i++) {
			updateRelation("sale_campaign", "contact_extra_fields", "extra_fields"+i+"_id",  "name", sourceWS, targetWS);
		}

		updateRelation("sale_campaign", "stock_category", "stock_category_id", "name", sourceWS, targetWS);
	}

	@Override
	public void destroyInfo(int targetWS) {
		executeDeleteQueryForInfoTables("sale_campaign", targetWS);
		executeDeleteQueryForInfoTables("sale_seller", targetWS);
	}

	@Override
	public Module getModule() {
		return Module.sale;
	}

}
