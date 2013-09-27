package comjiangqq.media.activiy;

import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btnSimpleDraw;
	private Button btnTimerDraw;
	private SurfaceView sfv;
	SurfaceHolder sfh;  
	private Timer mTimer;  
	private MyTimerTask mTimerTask;  
	
	int Y_axis[],//�������Ҳ���Y���ϵĵ�  
    centerY,//������  
    oldX,oldY,//��һ��XY��   
    currentX;//��ǰ���Ƶ���X���ϵĵ�  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnSimpleDraw=(Button)this.findViewById(R.id.button01);
		btnTimerDraw=(Button)this.findViewById(R.id.button02);
		btnSimpleDraw.setOnClickListener(new ClickEvent());
		btnTimerDraw.setOnClickListener(new ClickEvent());
		sfv=(SurfaceView)this.findViewById(R.id.surfaceView);
		sfh=sfv.getHolder();
	    
		 mTimer = new Timer();  
	     mTimerTask = new MyTimerTask();  
	     
	     //��ʼ��Y������
	     centerY=(getWindowManager().getDefaultDisplay().getHeight()-sfv.getTop())/2;
	     Y_axis=new int[getWindowManager().getDefaultDisplay().getWidth()]; //�����Ҫ�滭�ĵ���
	     for(int i=1;i<Y_axis.length;i++){ //�������Ҳ�  ����Y������ĵ�
	    	 Y_axis[i-1]=centerY-(int)(100*Math.sin(i*2*Math.PI/180));
	    	 
	     }
	}

	 class ClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			 switch (v.getId()) {
			case R.id.button01:
				 SimpleDraw(Y_axis.length-1);//ֱ�ӻ������Ҳ� 
				
				break;

			case R.id.button02:
				 oldY = centerY;  
	             mTimer.schedule(mTimerTask, 0, 5);//��̬�������Ҳ�  
				break;
			}
		}  
		 
	 }
	
	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			SimpleDraw(currentX);  
            currentX++;//��ǰ��  
            if (currentX == Y_axis.length - 1) {//��������յ㣬����������  
                ClearDraw();  
                currentX = 0;  
                oldY = centerY;  
            }  
		}  
		
	}
	
	void SimpleDraw(int length){
		if(length==0)
			oldX=0;
		Canvas canvas=sfh.lockCanvas(new Rect(oldX, 0, oldX+length, getWindowManager().getDefaultDisplay().getHeight()));//�ؼ�:��ȡ����
		Log.i("jiangqq",  
                String.valueOf(oldX) + "," + String.valueOf(oldX + length));  
		Paint mPaint=new Paint();
		mPaint.setColor(Color.GREEN);//����Ϊ��ɫ
		mPaint.setStrokeWidth(2);    //���û��ʴ�ϸ
		
		int y;
		for(int i=oldX+1;i<length;i++){
			y=Y_axis[i-1];
			canvas.drawLine(oldX, oldY, i, y, mPaint);
			oldX=i;
			oldY=y;
		}
	    sfh.unlockCanvasAndPost(canvas); //��������
	}
	
	void ClearDraw(){
		Canvas canvas=sfh.lockCanvas();
		canvas.drawColor(Color.BLACK);
		sfh.unlockCanvasAndPost(canvas);
	}
}
