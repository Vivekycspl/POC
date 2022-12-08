package com.ycspl.bingaa

object Utility {

    fun fetchDocumentType(value: Int): List<DropDownMenuModel> {
        var list = emptyList<DropDownMenuModel>()
        when(value) {
            80 -> {
                list = listOf<DropDownMenuModel>(DropDownMenuModel(1, "Others"))
            }
            62 -> {
                list = listOf<DropDownMenuModel>(
                    DropDownMenuModel(1, "Adhar Card"),
                    DropDownMenuModel(2, "Driving License"),
                    DropDownMenuModel(3, "Voter Identity"),
                    DropDownMenuModel(4, "Others"),
                )
            }
            81 -> {
                list = listOf<DropDownMenuModel>(
                    DropDownMenuModel(1, "Adhar Card"),
                    DropDownMenuModel(2, "Driving License"),
                    DropDownMenuModel(3, "Voter Identity"),
                    DropDownMenuModel(4, "Others"),
                )
            }
            74 -> {
                list = listOf<DropDownMenuModel>(DropDownMenuModel(1, "Others"))
            }
            64 -> {
                list = listOf<DropDownMenuModel>(DropDownMenuModel(1, "Old Tax Bill"))
            }
            82 -> {
                list = listOf<DropDownMenuModel>(
                    DropDownMenuModel(1, "Adhar Card"),
                    DropDownMenuModel(2, "Driving License"),
                    DropDownMenuModel(3, "Voter Identity"),
                    DropDownMenuModel(4, "Passport"),
                    DropDownMenuModel(5, "PanCard"),
                    DropDownMenuModel(6, "Ration Card"),
                    DropDownMenuModel(7, "Revenue Authority Certificate"),
                    DropDownMenuModel(8, "Bank Passbook"),
                    DropDownMenuModel(9, "BPL Card"),
                    DropDownMenuModel(10, "Family Id"),
                    DropDownMenuModel(11, "Address Proof"),
                    DropDownMenuModel(12, "Registry"),
                    DropDownMenuModel(13, "Electricity Bill"),
                    DropDownMenuModel(14, "Water Bill"),
                    DropDownMenuModel(15, "Rebate Proof"),
                    DropDownMenuModel(16, "Rebate Proof"),
                    DropDownMenuModel(17, "Old Tax Bill"),
                    DropDownMenuModel(18, "Committee Decision"),
                    DropDownMenuModel(19, "Citizen Documents"),
                    DropDownMenuModel(20, "Others"),
                )
            }
        }
        return list
    }

}