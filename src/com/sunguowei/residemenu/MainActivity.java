package com.sunguowei.residemenu;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.sunguowei.callback.CallBack;
/**
 * 仿QQ双侧侧滑效果
 * @author 有点凉了
 * QQ群：123869487
 * 求基友共同进步，求大神入群指点
 *
 */
public class MainActivity extends SlidingFragmentActivity implements OnClickListener,CallBack/*implements OnGestureListener*/ {
	private static final String TAG="MainActivity";
    private Fragment mContent;
    SlidingMenu sm = null;
   private CanvasTransformer mTransformer;  
   private CanvasTransformer mTransformer2;  
   
   
   private Button button_main_open_left;
   private Button button_main_open_right;
   private TextView main_one;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_content);
        
        button_main_open_left = (Button) findViewById(R.id.button_main_open_left);
        button_main_open_right = (Button) findViewById(R.id.button_main_open_right);
        main_one = (TextView) findViewById(R.id.main_one);
        button_main_open_left.setOnClickListener(this);
        button_main_open_right.setOnClickListener(this);
        setBehindContentView(R.layout.menu_frame);//加载左侧界面
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment()).commit();
        //主界面动画
        mTransformer = new CanvasTransformer() {
			
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
//				float scale = (float) (percentOpen*0.25 + 0.75); 
				float scale = (float) (1 - percentOpen * 0.25);
                canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);              
			}
		};
		//背景层动画
		mTransformer2 = new CanvasTransformer() {
			
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
                canvas.scale(scale, scale, 0,
                        canvas.getHeight() / 2);           
			}
		};
		/**
		 * 以下是slidingmenu惯用操作
		 */
        sm = getSlidingMenu();
        sm.setSecondaryMenu(R.layout.menu_frame2);//加载右侧界面
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame2, new MenuFragment2()).commit();
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeEnabled(false);
        sm.setBehindScrollScale(0.25f);
        sm.setFadeDegree(0.75f);
        sm.setMode(SlidingMenu.LEFT_RIGHT);
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        sm.setBackgroundImage(R.drawable.img_frame_background);
        sm.setBehindWidth((int)(getWindowManager().getDefaultDisplay().getWidth() / 1.35));
        sm.setBehindCanvasTransformer(mTransformer2);
        sm.setAboveCanvasTransformer(mTransformer);
       
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_main_open_left:
			setToggle();
			break;
		case R.id.button_main_open_right:
			sm.showSecondaryMenu();
			break;

		default:
			break;
		}
	}

	
	public void setToggle(){
		toggle();
	}
	@Override
	public void SuccessText(String text) {
		// TODO Auto-generated method stub
		Log.i(TAG, "==-->text:="+text);
		main_one.setText(text);
	}
	
}
