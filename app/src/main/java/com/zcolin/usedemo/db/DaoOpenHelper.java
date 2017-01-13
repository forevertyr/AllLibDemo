/***********************************************************
 * author   colin
 * company  fosung
 * email    wanglin2046@126.com
 * date     16-7-15 上午9:54
 **********************************************************/

package com.zcolin.usedemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fosung.usedemo.greendao.dao.DaoMaster;

import org.greenrobot.greendao.database.Database;

import static com.fosung.usedemo.greendao.dao.DaoMaster.dropAllTables;


/**
 * 默认的DaoMaster.OpenHelper在碰到数据库升级的时候会删除旧的表来创建新的表，
 * 这样就会导致旧表的数据全部丢失了，所以要封装DaoMaster.OpenHelper来实现数据库升级
 */
public class DaoOpenHelper extends DaoMaster.OpenHelper {

    public DaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        dropAllTables(db, true);
        onCreate(db);

        //TODO 如果需要保存数据， 数据库升级时需要在此写升级语句
        switch (oldVersion) {
            case 1:

                //创建新表，注意createTable()是静态方法
                // SchoolDao.createTable(db, true);     

                // 加入新字段
                // db.execSQL("ALTER TABLE 'moments' ADD 'audio_path' TEXT;");  
                break;
        }
    }


}