package com.example.myapplication.map;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

import com.example.myapplication.FightActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MapListN;
import com.example.myapplication.R;
import com.example.myapplication.model.ModelMonster;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MapView extends View implements View.OnClickListener {

    int TILE_WIDTH = 32;
    int TILE_HEIGHT = 32;
    String TAG = "MapView";

    final static int TILE_WIDTH_COUNT = 20;
    final static int TILE_HEIGHT_COUNT = 20;

    final static int TILE_NULL = 0;

    // 地图的宽高的
    final static int MAP_WIDTH = 640;
    final static int MAP_HEIGHT = 640;

    //镜头移动范围
    final static int CAMERA_MOVE = 10;

    int mScreenWidth = 0;
    int mScreenHeight = 0;

    //游戏地图资源
    Bitmap mBitmap = null;
    Bitmap mBitmap2 = null;

    //资源文件
    Resources mResources = null;

    //游戏画笔
    Paint mPaint = null;

    //横向纵向tile块的数量
    int mWidthTileCount = 0;
    int mHeightTileCount = 0;

    //横向纵向tile块的数量
    int mBitMapWidth = 0;
    int mBitMapHeight = 0;

    //摄像头的焦点  0.0点为焦点在中心。
    int mCameraPosX = 0;
    int mCameraPosY = 0;

    //地图左上角锚点坐标
    int mMapPosX = 0;
    int mMapPosY = 0;
    int mHeroPosX = 0;
    int mHeroPosY = 0;

    //记录上次时间
    long statrTime = 0;
    long statrTimeHero = 0;
    long statrTimeWeapon = 0;
    long statrTimeimg = 0;

    //heroBitmap
    Bitmap hBitmap;
    int hBitmapWidth = 0;
    int hBitmapHeight = 0;
    //weaponBitmap
    Bitmap wpBitmap;
    int wpBitmapWidth = 0;
    int wpBitmapHeight = 0;
    int[][] weaponLcation = {
            {38, 28}, {35, 25}, {30, 26}, {53, 23}
    };

    Canvas mcanvas;
    Paint paint;
    //方位
    int orientation;
    //比例
    float scaleX;
    float scaleY;

    private int left = 1;
    private int up = 2;
    private int right = 3;
    private int down = 0;
    private int move = 0;
    public int moveWeapon = 3;
    public boolean btnWeapon = false;

    public float smallCenterX = 120, smallCenterY = 120, smallCenterR = 15;
    public float BigCenterX = 120, BigCenterY = 120, BigCenterR = 40;

    MapInit mapInit;

    ArrayList<MapList> mapLists;
    int MapCount = 0;

    ArrayList<ModelMonster> modelMonsters;
    int monsterSrcX = 0;
    int monsterSrcY = 0;

    ArrayList<Bitmap> imgs;
    boolean imgsB = false;
    int imgCount = 0;

    Context context;

    public MapView(Context context, int screenWidth, int screenHeight) {
        super(context);
        this.context = context;
        initialize(context, screenWidth, screenHeight);
        initMapList(context);
        initModelView(context);
        initImgs(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initMapList(Context context) {
        mapLists = new ArrayList<>();
        MapList mapList = new MapList();
        mapList.getList().add(MapListN.my_first_map_map0);
        mapList.getList().add(MapListN.my_first_map_map1);
        mapList.getList().add(MapListN.my_first_map_map2);
        mapList.getBitmaps().add(mBitmap);
        mapList.getBitmaps().add(mBitmap2);
        mapLists.add(mapList);
        MapList mapList1 = new MapList();
        mapList1.getList().add(MapListN.my_secont_map_map0);
        mapList1.getList().add(MapListN.my_secont_map_map1);
        mapList1.getList().add(MapListN.my_secont_map_map2);
        mapList1.getList().add(MapListN.my_secont_map_map3);
        mapList1.getBitmaps().add(mapInit.ReadBitMap(context, R.drawable.dungeon_a2, scaleX));
        mapList1.getBitmaps().add(mapInit.ReadBitMap(context, R.drawable.dungeon_a2, scaleX));
        mapList1.getBitmaps().add(mapInit.ReadBitMap(context, R.drawable.fw_horror_tile_c, scaleX));
        mapLists.add(mapList1);
        MapList mapList2 = new MapList();
        mapList2.getList().add(MapListN.my_thirdly_map_map0);
        mapList2.getList().add(MapListN.my_thirdly_map_map1);
        mapList2.getList().add(MapListN.my_thirdly_map_map2);
        mapList2.getBitmaps().add(mapInit.ReadBitMap(context, R.drawable.tilea2, scaleX));
        mapList2.getBitmaps().add(mapInit.ReadBitMap(context, R.drawable.tilec_interior, scaleX));
        mapLists.add(mapList2);
    }

    private void initModelView(Context context) {
        modelMonsters = new ArrayList<>();
        ModelMonster modelMonster = new ModelMonster();
        modelMonster.setX((int) (300 * scaleX));
        modelMonster.setY((int) (400 * scaleX));
        modelMonster.setBitmap(mapInit.ReadBitMap(context, R.drawable.monster1, scaleX));
        modelMonster.setType(0);
        modelMonster.setWidth(modelMonster.getBitmap().getWidth() / 2);
        modelMonster.setHeight(modelMonster.getBitmap().getHeight() / 2);
        modelMonster.setMapCount(0);
        modelMonsters.add(modelMonster);
    }

    private void initImgs(Context context) {
        imgs = new ArrayList<>();
        imgs.add(mapInit.ReadBitMap(context, R.drawable.img0, scaleX));
        imgs.add(mapInit.ReadBitMap(context, R.drawable.img1, scaleX));
        imgs.add(mapInit.ReadBitMap(context, R.drawable.img2, scaleX));
        imgs.add(mapInit.ReadBitMap(context, R.drawable.img3, scaleX));
        imgs.add(mapInit.ReadBitMap(context, R.drawable.img4, scaleX));
        imgs.add(mapInit.ReadBitMap(context, R.drawable.img5, scaleX));
        imgs.add(mapInit.ReadBitMap(context, R.drawable.img6, scaleX));
        imgs.add(mapInit.ReadBitMap(context, R.drawable.img7, scaleX));
    }

    private void initialize(Context context, int screenWidth, int screenHeight) {
        mapInit = new MapInit();
        mScreenHeight = screenHeight;
        mScreenWidth = screenWidth;
        scaleX = (float) (screenWidth / 480.0);
        scaleY = (float) (screenHeight / 270.0);
        BigCenterR *= scaleX;
        smallCenterR = BigCenterR / 3;
        mPaint = new Paint();
        //初始化地图
        mBitmap = ReadBitMap(context, R.drawable.dungeon_a2);
        mBitmap2 = ReadBitMap(context, R.drawable.world_b);
        int x = mBitmap.getWidth() / 32;
        int y = mBitmap.getHeight() / 32;
        mBitmap = big(mBitmap);
        mBitmap2 = big(mBitmap2);
        mBitMapWidth = mBitmap.getWidth();
        mBitMapHeight = mBitmap.getHeight();
        TILE_WIDTH = mBitMapWidth / x;
        TILE_HEIGHT = mBitMapHeight / y;
        mWidthTileCount = mBitMapWidth / TILE_WIDTH;
        mHeightTileCount = mBitMapHeight / TILE_HEIGHT;
        //初始化英雄
        hBitmap = mapInit.ReadBitMap(context, R.drawable.hero1, scaleX);
        hBitmapWidth = hBitmap.getWidth() / 4;
        hBitmapHeight = hBitmap.getHeight() / 4;
        mHeroPosX = screenWidth / 2;
        mHeroPosY = screenHeight / 2;
        //初始化武器
        wpBitmap = mapInit.ReadBitMap(context, R.drawable.weapon1, scaleX);
        wpBitmapWidth = wpBitmap.getWidth() / 3;
        wpBitmapHeight = wpBitmap.getHeight() / 4;
        //初始化操作工具
        BigCenterX = smallCenterX = screenWidth / 4;
        BigCenterY = smallCenterY = screenHeight / 4 * 3;
        mMapPosX = (int) -(30 * scaleX);
        mMapPosY = (int) -(100 * scaleX);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mcanvas = canvas;
        //地图
        DrawMap(canvas, mPaint, mapLists.get(MapCount));
        //坐标
        DrawRectText(canvas);
        //怪物
        DrawMonster(canvas, mPaint, modelMonsters);
        //英雄
        DrawHero(canvas, mPaint, hBitmap, mHeroPosX, mHeroPosY, move * hBitmapWidth, orientation * hBitmapHeight);
        //武器-->拾取效果
        DrawWeapon(canvas, mPaint, wpBitmap, mHeroPosX - weaponLcation[orientation][0] * (int) scaleX, mHeroPosY - weaponLcation[orientation][1] * (int) scaleX, moveWeapon * wpBitmapWidth, orientation * wpBitmapHeight);
        //移动
        move();
        //操作
        DrawOperation(canvas);
        //特效
//        attackpecialEffects(canvas);
        super.onDraw(canvas);
        invalidate();
    }

    private void DrawOperation(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setAlpha(50);
        canvas.drawCircle(BigCenterX, BigCenterY, BigCenterR, paint);
        paint.setColor(Color.parseColor("#696767"));
        paint.setAlpha(150);
        canvas.drawCircle(smallCenterX, smallCenterY, smallCenterR, paint);
    }

    private void DrawHero(Canvas canvas, Paint mPaint, Bitmap hBitmap, int x, int y, int src_x, int src_y) {
        canvas.save();
        canvas.clipRect(x, y, x + hBitmapWidth, y + hBitmapHeight);
        canvas.drawBitmap(hBitmap, x - src_x, y - src_y, mPaint);
        canvas.restore();
        invalidate();
    }

    private void DrawMonster(Canvas canvas, Paint mPaint, ArrayList<ModelMonster> modelMonsters) {
        canvas.save();
        for (int i = 0; i < modelMonsters.size(); i++) {
            if (MapCount == modelMonsters.get(i).getMapCount()) {
                int x = modelMonsters.get(i).getX() + mMapPosX;
                int y = modelMonsters.get(i).getY() + mMapPosY;
                int width = modelMonsters.get(i).getWidth();
                int height = modelMonsters.get(i).getHeight();
                canvas.clipRect(x, y, x + width, y + height);
                canvas.drawBitmap(modelMonsters.get(i).getBitmap(), x, y, mPaint);
            }
        }
        canvas.restore();
        invalidate();
    }

    private void DrawWeapon(Canvas canvas, Paint mPaint, Bitmap wpBitmap, int x, int y, int src_x, int src_y) {
        canvas.save();
        canvas.clipRect(x, y, x + wpBitmapWidth, y + wpBitmapHeight);
        canvas.drawBitmap(wpBitmap, x - src_x, y - src_y, mPaint);
        canvas.restore();
        invalidate();
    }

    private void DrawMap(Canvas canvas, Paint paint, MapList mapList) {
        int i = 0, j = 0;
        int x = mMapPosX / TILE_WIDTH;
        int y = mMapPosY / TILE_HEIGHT;

        float a = ((float) mScreenHeight) / TILE_HEIGHT;
        int jy = mScreenHeight / TILE_HEIGHT;
        int jx = mScreenWidth / TILE_WIDTH;
//            Log.i("view", "x" + x);
        if (-x + jx >= mapList.getList().get(0).length - 1) {
            if (-x + jx == mapList.getList().get(0).length - 1) {
                jx++;
            }
        } else {
            jx += 2;
        }
        if (-y + jy >= mapList.getList().get(0).length - 1) {
            if (-y + jy == mapList.getList().get(0).length - 1) {
                jy++;
            }
        } else {
            jy += 2;
        }
        for (i = -y; i < jy - y; i++) {
            for (j = -x; j < jx - x; j++) {
                //绘制地图n层
                for (int k = 0; k < mapList.getList().size() - 1; k++) {
                    int ViewID = mapList.getList().get(k)[i][j];
                    if (ViewID > TILE_NULL) {
                        DrawMapTile(ViewID, canvas, paint, mapList.getBitmaps().get(k), j * TILE_WIDTH + mMapPosX, i * TILE_HEIGHT + mMapPosY);
                    }
                }
            }
        }
    }

    public void DrawMapTile(int id, Canvas canvas, Paint paint, Bitmap bitmap, int x, int y) {
        id--;
        int count = id / mWidthTileCount;//第几行
        int bitmapX = (id - (count * mWidthTileCount)) * TILE_WIDTH;//
        int bitmapY = count * TILE_HEIGHT;
        DrawClipImage(canvas, paint, bitmap, x, y, bitmapX, bitmapY, TILE_WIDTH, TILE_HEIGHT);
    }

    private void DrawClipImage(Canvas canvas, Paint paint, Bitmap bitmap, int x, int y, int src_x, int src_y, int src_xp, int src_yp) {
        canvas.save();
        canvas.clipRect(x, y, x + src_xp, y + src_yp);
        canvas.drawBitmap(bitmap, x - src_x, y - src_y, paint);
        canvas.restore();
    }

    public Bitmap ReadBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    private Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleX);  //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    private void DrawRectText(Canvas canvas) {
//            canvas.clipRect(0, 0, mScreenWidth, 30);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(scaleX * 12);
        canvas.drawText("(" + (-mMapPosX) + "," + (-mMapPosY) + ")", 0, scaleX * 12, paint);
    }

    @Override
    public void onClick(View v) {
        Log.i("view", v.getY() + "KeyEvent:" + v.getX());
    }

    //计算弧长
    double getRad(float px1, float py1, float px2, float py2) {
        //得到两点X的距离
        float dx = px2 - px1;
        //得到两点Y的距离
        float dy = py1 - py2;
        float c = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        float cosAngle = dx / c;
        float rad = (float) Math.acos(cosAngle);
        return rad;
    }

    boolean tag = false;


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (((MainActivity) context).relativeLayout.getVisibility() == View.VISIBLE) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                smallCenterX = BigCenterX;
                smallCenterY = BigCenterY;
                move = 0;
                tag = false;
                ((MainActivity) context).getMediaPlayer().pause();
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                control(event);
                break;
            default:

                break;
        }
        invalidate();
        return true;
    }

    private void control(MotionEvent event) {
        tag = true;
        ((MainActivity) context).getMediaPlayer().start();
        double red = getRad(BigCenterX, BigCenterY, event.getX(), event.getY());
        if (red < 3.1415927 / 4.0) {//右移动
            smallCenterX = BigCenterX + BigCenterR;
            smallCenterY = BigCenterY;
            orientation = 3;
        } else if (red < (3.1415927 / 4.0) * 3) {
            smallCenterX = BigCenterX;
            if (event.getY() > BigCenterY) {//下移动
                smallCenterY = BigCenterY + BigCenterR;
                orientation = 0;
            } else {//上移动
                smallCenterY = BigCenterY - BigCenterR;
                orientation = 2;
            }
        } else {//左移动
            smallCenterX = BigCenterX - BigCenterR;
            smallCenterY = BigCenterY;
            orientation = 1;
        }
    }

    private void move() {
        long nowTime = System.currentTimeMillis();
        if (btnWeapon) {
            if (nowTime - statrTimeWeapon > 100) {
                if (moveWeapon == 4) {
                    btnWeapon = false;
                    if (!tag) {
                        move = -1;
                    }
                    //攻击特效方法和对话的方法
//                    attack();
                }
                moveWeapon++;
                if (!tag) {
                    move++;
                }
                statrTimeWeapon = nowTime;
            }
        }
        if (tag) {
            if (nowTime - statrTimeHero > 90) {
                move++;
                statrTimeHero = nowTime;
            }
            if (nowTime - statrTime > 60) {
                int oldmHeroPosX = mHeroPosX;
                int oldmMapPosX = mMapPosX;
                int oldmHeroPosY = mHeroPosY;
                int oldmMapPosY = mMapPosY;
                switch (orientation) {
                    case 0://下
                        if (-mMapPosY >= TILE_HEIGHT * 20 - mScreenHeight || mHeroPosY < (mScreenHeight / 3)) {
                            mHeroPosY += (4 * scaleX);
                            if (mHeroPosY > mScreenHeight - hBitmapHeight)
                                mHeroPosY -= (4 * scaleX);
                        } else {
                            mMapPosY -= (4 * scaleX);
                        }
                        break;
                    case 1://左
                        if (mMapPosX >= 0 || mHeroPosX > ((mScreenWidth) / 3) * 2) {
                            mHeroPosX -= (4 * scaleX);
                            if (mHeroPosX < 0) mHeroPosX = 0;
                        } else {
                            mMapPosX += (4 * scaleX);
                        }
                        break;
                    case 2://上
                        if (mMapPosY >= 0 || mHeroPosY > (mScreenHeight / 3) * 2) {
                            mHeroPosY -= (4 * scaleX);
                            if (mHeroPosY < 0) mHeroPosY = 0;
                        } else {
                            mMapPosY += (4 * scaleX);
                        }
                        break;
                    case 3://右
//                            Log.i("view", "mHeroPosX:" + mHeroPosX + " 最终：" + (mScreenWidth));
                        if (-mMapPosX >= TILE_WIDTH * 20 - mScreenWidth || mHeroPosX < (mScreenWidth / 3)) {
                            mHeroPosX += (4 * scaleX);
                            if (mHeroPosX > mScreenWidth - hBitmapWidth)
                                mHeroPosX -= (4 * scaleX);
                        } else {
                            mMapPosX -= (4 * scaleX);
                        }
                        break;
                }
                //碰撞检测
                int x = ((-mMapPosX) + mHeroPosX + hBitmapWidth / 2) / TILE_WIDTH;
                int y = ((-mMapPosY) + mHeroPosY + ((hBitmapHeight / 5) * 4)) / TILE_HEIGHT;
                Boolean monster_Collision = false;
                Log.i(TAG, "\nmMapPosX=" + mMapPosX + "\nmMapPosY=" + mMapPosY);
                Log.i(TAG, "\ntag=" + tag);
                Iterator<ModelMonster> modelMonsterIterator = modelMonsters.iterator();
                while (modelMonsterIterator.hasNext()) {
                    ModelMonster modelMonster = modelMonsterIterator.next();
                    int monster_x = modelMonster.getX() + mMapPosX;
                    int monster_y = modelMonster.getY() + mMapPosY;
                    int width = modelMonster.getWidth();
                    int height = modelMonster.getHeight();
                    if (isCollsionWithRect(mHeroPosX, mHeroPosY, hBitmapWidth, hBitmapHeight,
                            monster_x, monster_y, width - (10 * (int) scaleX), height)
                            && modelMonster.getMapCount() == MapCount) {
                        monster_Collision = true;
                        modelMonsterIterator.remove();
                        break;
                    }
                }
                if (0 != mapLists.get(MapCount).getList().get(mapLists.get(MapCount).getList().size() - 1)[y][x]
                        || monster_Collision) {
                    mMapPosY = oldmMapPosY;
                    mHeroPosY = oldmHeroPosY;
                    mMapPosX = oldmMapPosX;
                    mHeroPosX = oldmHeroPosX;
                    //变换地图
                    setMapCount(mapLists.get(MapCount).getList().get(mapLists.get(MapCount).getList().size() - 1)[y][x]);
                }
                Log.i(TAG, "move: " + monster_Collision);
                if (monster_Collision) {
                    smallCenterX = BigCenterX;
                    smallCenterY = BigCenterY;
                    move = 0;
                    tag = false;
                    ((MainActivity) context).getMediaPlayer().pause();
                    Fight();

                }
                statrTime = nowTime;
            }
        }
        if (move == 4) move = 0;
        //交互回写本机hero操作情况
    }

    public boolean isCollsionWithRect(int x1, int y1, int w1, int h1,
                                      int x2, int y2, int w2, int h2) {
        if (x1 >= x2 && x1 >= x2 + w2) {
            return false;
        } else if (x1 <= x2 && x1 + w1 <= x2) {
            return false;
        } else if (y1 >= y2 && y1 >= y2 + h2) {
            return false;
        } else if (y1 <= y2 && y1 + h1 <= y2) {
            return false;
        }
        return true;
    }

    private void attack() {
        imgsB = true;
    }

    private void Fight() {
        context.startActivity(new Intent(context, FightActivity.class));
    }

    private void attackpecialEffects(Canvas canvas) {//动作类玩法
        if (imgCount >= imgs.size() - 1) {
            imgsB = false;
            imgCount = 0;
        }
        if (imgsB) {
            for (int i = 0; i < modelMonsters.size(); i++) {
                if (MapCount == modelMonsters.get(i).getMapCount()) {
                    int x = modelMonsters.get(i).getX() + mMapPosX;
                    int y = modelMonsters.get(i).getY() + mMapPosY;
                    int width = modelMonsters.get(i).getWidth();
                    int height = modelMonsters.get(i).getHeight();
                    canvas.save();
                    switch (orientation) {
                        case 0://下
                            if (mHeroPosY + 30 * scaleX > y && mHeroPosY + 30 * scaleX < y + height) {
                                drawTX(x, y, width, height);
                            }
                            break;
                        case 1://左
                            if (mHeroPosX - 30 * scaleX > x && mHeroPosX - 30 * scaleX < x + width) {
                                drawTX(x, y, width, height);
                            }
                            break;
                        case 2://上
                            if (mHeroPosY - 30 * scaleX > y && mHeroPosY - 30 * scaleX < y + height) {
                                drawTX(x, y, width, height);

                            }
                            break;
                        case 3://右
//                            Log.i("view", "mHeroPosX:" + mHeroPosX + " 最终：" + (mScreenWidth));
                            if (mHeroPosX + 30 * scaleX > x && mHeroPosX + 30 * scaleX < x + width) {
                                drawTX(x, y, width, height);
                            }
                            break;
                    }
                    canvas.restore();
                }
                long nowTime = System.currentTimeMillis();
                if (nowTime - statrTimeimg > 100) {
                    Log.i("attackpecialEffects: ", "attackpecialEffects: " + imgCount);
                    statrTimeimg = nowTime;
                    imgCount++;
                }
            }
        }

    }

    void drawTX(int x, int y, int width, int height) {
        mcanvas.clipRect(x + width + 25 * scaleX - imgs.get(imgCount).getWidth(), y + height - imgs.get(imgCount).getHeight()
                , x + width + 25 * scaleX, y + height);
        mcanvas.drawBitmap(imgs.get(imgCount), x + width - imgs.get(imgCount).getWidth() + 25 * scaleX, y + height - imgs.get(imgCount).getHeight()
                , paint);
    }

    private void setMapCount(int sz) {
        switch (sz) {
            case 1:
                MapCount = 1;
                mMapPosY = 0;
                mMapPosX = 0;
                mHeroPosX = mScreenWidth / 2;
                mHeroPosY = mScreenHeight / 2;
                break;
            case 2:
                MapCount = 2;
                mMapPosY = 0;
                mMapPosX = (int) (-30 * scaleX);
                mHeroPosX = mScreenWidth / 2;
                mHeroPosY = mScreenHeight / 2;
                break;
            case -1:
                MapCount = 0;
                mMapPosY = 0;
                mMapPosX = 0;
                mHeroPosX = mScreenWidth / 2;
                mHeroPosY = mScreenHeight / 2;
                break;
        }
    }
}
