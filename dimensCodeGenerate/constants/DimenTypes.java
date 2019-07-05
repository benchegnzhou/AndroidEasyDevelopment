package com.smart.myapplication.dimens.constants;


public enum DimenTypes {

    //适配Android 3.2以上   大部分手机的sw值集中在  300-460之间
	 DP_sw__300(300),  // values-sw300
	 DP_sw__310(310),
	 DP_sw__320(320),
	 DP_sw__320(330),
	 DP_sw__320(340),
	 DP_sw__320(350),
	 DP_sw__320(360),
	 DP_sw__320(370),
	 DP_sw__320(380),
	 DP_sw__320(390),
	 DP_sw__320(400),
	 DP_sw__320(410),
	 DP_sw__320(420),
	 DP_sw__320(430),
	 DP_sw__320(440),
	 DP_sw__320(450),
	 DP_sw__320(460),
	 DP_sw__320(470),
	 DP_sw__320(480);
	// 想生成多少自己以此类推
  

    /**
     * 屏幕最小宽度
     */
    private int swWidthDp;




    DimenTypes(int swWidthDp) {

        this.swWidthDp = swWidthDp;
    }

    public int getSwWidthDp() {
        return swWidthDp;
    }

    public void setSwWidthDp(int swWidthDp) {
        this.swWidthDp = swWidthDp;
    }

}
