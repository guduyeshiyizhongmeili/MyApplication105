package com.bwie.test.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bwie.test.myapplication.gen.DaoMaster;
import com.bwie.test.myapplication.gen.DaoSession;
import com.bwie.test.myapplication.gen.UserDao;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Button
     */
    private Button mButton;
    private UserDao userDao;
    private User user;
    /**
     * 删除
     */
    private Button mButton2;
    /**
     * 修改
     */
    private Button mButton4;
    /**
     * 查询
     */
    private Button mButton6;
    private RelativeLayout mActivityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();


        userDao.deleteAll();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(this);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton4.setOnClickListener(this);
        mButton6 = (Button) findViewById(R.id.button6);
        mButton6.setOnClickListener(this);
        mActivityMain = (RelativeLayout) findViewById(R.id.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.button:
                for (int i = 0; i < 10; i++) {
                    user = new User(null, "zhangsan" + i, "张三");
                    userDao.insert(user);
                }

                break;
            case R.id.button2:
                List<User> userList = (List<User>) userDao.queryBuilder().where(UserDao.Properties.Username.eq("张三0")).build().list();
                for (User user : userList) {
                    userDao.delete(user);
                }
                break;
            case R.id.button4:
                List<User> user2 = (List<User>) userDao.queryBuilder()
                        .where(UserDao.Properties.Username.eq("二狗子")).build().list();
                //查询出后如果集合user2是空，证明你要修改的那个不存在，
                if (user2 == null) {
                    Toast.makeText(MainActivity.this, "用户不存在!", Toast.LENGTH_SHORT).show();
                }else{
//                    user2.setUsername("儿子");
//                    userDao.update(user2);
                    //查询出后遍历集合，一条一条的修改。
                    for (int i = 0; i < user2.size(); i++) {
                        //Log.d("google_lenve","id========="+list.get(i).getId()+ "name===== " + list.get(i).getUsername()+"age===="+list.get(i).getNickname());
                        user2.get(i).setUsername("将定燃");
                        userDao.update(user2.get(i));
                    }

                }
                break;
            case R.id.button6:
                List<User> list = userDao.queryBuilder()
                        .build().list();
                //之后就是遍历查询出来的集合
                for (int i = 0; i < list.size(); i++) {
                    Log.d("google_lenve","id========="+list.get(i).getId()+ "name===== " + list.get(i).getUsername()+"age===="+list.get(i).getNickname());
                }
                break;
        }
    }
}
