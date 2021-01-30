package com.cegep.epicure.list.callback;

import com.cegep.epicure.model.PreparationStep;

public interface RemoveItemClickListener {

    void onRemoveClick(PreparationStep item, int position);

}
