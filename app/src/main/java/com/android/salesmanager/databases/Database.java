package com.android.salesmanager.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.salesmanager.BuildConfig;
import com.android.salesmanager.R;
import com.android.salesmanager.models.SanPham;
import com.android.salesmanager.models.SearchType;
import com.android.salesmanager.utils.SystemTime;
import com.android.salesmanager.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public SQLiteDatabase sqLiteDatabase;

    public static int VERSION = 1;
    public static final String DATABASE_NAME = "database.db";
    public static final String TABLE_KHO = "kho";
    private static final String ID = "id";
    private static final String MA = "ma";
    private static final String TEN = "ten";
    private static final String TEN_2 = "ten_2";
    private static final String SL_NHAP = "sl_nhap";
    private static final String SL_TON = "sl_ton";
    private static final String GIA_DX = "gia_dx";
    private static final String GIA_NHAP = "gia_nhap";
    private static final String TRANG_THAI = "trang_thai";
    private static final String THOI_GIAN = "thoi_gian";
    private static final String GIA_BAN_RA = "gia_ban_ra";
    private static final String SL = "sl";

    private Context context;
    private static Database instance;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        this.sqLiteDatabase = getWritableDatabase();
    }

    public static Database getInstance(Context context) {
        if (instance == null) {
            instance = new Database(context);
        }
        return instance;
    }

    public void insertNote(SanPham sanPham) {
    }


    public interface DatabaseListener {
        void callback(Object[] objArr);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.sqLiteDatabase = db;
        String script = "CREATE TABLE " + TABLE_KHO + " (" +
                ID + " INTEGER PRIMARY KEY, " +
                MA + " TEXT, " +
                TEN + " TEXT, " +
                TEN_2 + "TEXT, " +
                SL_NHAP + " INTEGER, " +
                SL_TON + " INTEGER, " +
                GIA_DX + " INTEGER, " +
                GIA_NHAP + " INTEGER, " +
                TRANG_THAI + " INTEGER, " +
                THOI_GIAN + " INTEGER" + ")";
        this.sqLiteDatabase.execSQL(script);

        String script2 = "CREATE TABLE " + "Da_ban" + " (" +
                ID + " INTEGER PRIMARY KEY, " +
                MA + " TEXT, " +
                GIA_BAN_RA + " INTEGER, " +
                SL + " INTEGER, " +
                GIA_NHAP + " INTEGER, " +
                THOI_GIAN + " INTEGER" + ")";
        this.sqLiteDatabase.execSQL(script2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String themSPKho(SanPham sanPham) {
        if (sanPham.getMa().trim().equals("")) {
            return this.context.getResources().getString(R.string.ma_sp_khong_hop_le);
        }
        if (sanPham.getTen().trim().equals("")) {
            return this.context.getResources().getString(R.string.ten_sp_khong_hop_le);
        }
        if (sanPham.getSlNhap() < 0) {
            return this.context.getResources().getString(R.string.so_luong_khong_hop_le);
        }
        if (sanPham.getGiaNhap() < 0) {
            return this.context.getResources().getString(R.string.gia_nhap_khong_hop_le);
        }
        if (sanPham.getGiaDeXuat() < 0) {
            return this.context.getResources().getString(R.string.gia_dx_khong_hop_le);
        }
        if (existsMaSPKho(sanPham.getMa())) {
            return this.context.getResources().getString(R.string.ma_) + " " + sanPham.getMa() + " " + this.context.getResources().getString(R.string._da_ton_tai);
        }
        if (existsTenSPKho(sanPham.getTen())) {
            return this.context.getResources().getString(R.string.ten_san_pham_) + sanPham.getTen() + " " + this.context.getResources().getString(R.string._da_ton_tai);
        }


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MA, sanPham.getMa());
        contentValues.put(TEN, sanPham.getTen());
        contentValues.put(SL_NHAP, sanPham.getSlNhap());
        contentValues.put(SL_TON, sanPham.getSlNhap());
        contentValues.put(GIA_DX, sanPham.getGiaDeXuat());
        contentValues.put(GIA_NHAP, sanPham.getGiaNhap());
        contentValues.put(TRANG_THAI, "active");
        contentValues.put(THOI_GIAN, SystemTime.getInstance().getTime());

        try {
            db.insertOrThrow(TABLE_KHO, null, contentValues);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return this.context.getResources().getString(R.string.xay_ra_loi);
        }
    }

    public String themSPDaBan(SanPham sanPham) {
        if (sanPham == null) {
            return this.context.getResources().getString(R.string.chua_chon_sp);
        }
        if (sanPham.getBanVoiGia() < 0) {
            return this.context.getResources().getString(R.string.gia_ban_ra_khong_hop_le);
        }
        if (sanPham.getSl() < 0) {
            return this.context.getResources().getString(R.string.sl_san_pham_khong_hop_le);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma", sanPham.getMa());
        contentValues.put("gia_ban_ra", sanPham.getBanVoiGia());
        contentValues.put("sl", sanPham.getSl());
        contentValues.put("gia_nhap", sanPham.getGiaNhap());
        //contentValues.put("thoi_gian", sanPham.getThoiGian().trim().equals(BuildConfig.FLAVOR) ? SystemTime.getInstance().getTime() : SystemTime.getInstance().getTime(sanPham.getThoiGian().trim()));
        contentValues.put("thoi_gian", SystemTime.getInstance().getTime());
        StringBuilder stSqlUpdate = new StringBuilder("UPDATE kho SET sl_ton = sl_ton - ").append(Utils.numberFormat(sanPham.getSl()).replace(",", BuildConfig.FLAVOR)).append(" WHERE ma = '").append(sanPham.getMa()).append("';");
        Log.d("TTT", "stSqlUpdate: " + stSqlUpdate);
        try {
            this.sqLiteDatabase.execSQL(stSqlUpdate.toString());
            this.sqLiteDatabase.insertOrThrow("da_ban", null, contentValues);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return this.context.getResources().getString(R.string.xay_ra_loi);
        }
    }

    public String nhapThemSanPham(String ma, double soLuong) {
        StringBuilder stSqlUpdate = new StringBuilder("UPDATE kho SET sl_nhap = sl_nhap + ").append(soLuong).append(", sl_ton = sl_ton + ").append(soLuong).append(" WHERE ma = '").append(ma).append("';");
        Log.d("TTT", "sql: " + stSqlUpdate.toString());
        try {
            this.sqLiteDatabase.execSQL(stSqlUpdate.toString());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return this.context.getResources().getString(R.string.xay_ra_loi);
        }
    }

    public String suaSanPhamKho(SanPham sanPham, String tenCu) {
        if (sanPham.getTen().trim().equals(BuildConfig.FLAVOR)) {
            return this.context.getResources().getString(R.string.ten_sp_validate);
        }
        if (sanPham.getGiaNhap() < 0) {
            return this.context.getResources().getString(R.string.gia_nhap_khong_hop_le);
        }
        if (sanPham.getGiaDeXuat() < 0) {
            return this.context.getResources().getString(R.string.gia_dx_khong_hop_le);
        }
        if (!tenCu.equals(sanPham.getTen()) && existsTenSPKho(sanPham.getTen())) {
            return this.context.getResources().getString(R.string.san_pham_) + sanPham.getTen() + this.context.getResources().getString(R.string._da_ton_tai);
        }
        StringBuilder stSqlUpdate = new StringBuilder("UPDATE kho SET ten = '").append(sanPham.getTen()).append("', gia_nhap = ").append(sanPham.getGiaNhap()).append(", gia_dx = ").append(sanPham.getGiaDeXuat()).append(" WHERE ma = '").append(sanPham.getMa()).append("';");
        Log.d("TTT", "sql: " + stSqlUpdate.toString());
        try {
            this.sqLiteDatabase.execSQL(stSqlUpdate.toString());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return this.context.getResources().getString(R.string.xay_ra_loi);
        }
    }

    public String xoaSanPhamKho(String ma) {
        try {
            this.sqLiteDatabase.execSQL("DELETE FROM kho WHERE ma = '" + ma + "';");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return this.context.getResources().getString(R.string.xay_ra_loi);
        }
    }


    public boolean existsMaSPKho(String ma) {
        Cursor cursor = this.sqLiteDatabase.rawQuery("SELECT count(*) as 'count' FROM kho WHERE upper(ma) = upper('" + ma + "');", null);
        return cursor.moveToFirst() && cursor.getInt(cursor.getColumnIndex("count")) > 0;
    }

    public boolean existsTenSPKho(String ten) {
        Cursor cursor = this.sqLiteDatabase.rawQuery("SELECT count(*) as 'count' FROM kho WHERE upper(ten) = upper('" + ten + "');", null);
        return cursor.moveToFirst() && cursor.getInt(cursor.getColumnIndex("count")) > 0;
    }


    public AsyncTask getSPKho(DatabaseListener listener) {
        return getSPKho(null, null, listener);
    }

    public AsyncTask getSPKho(String key, SearchType searchType, DatabaseListener listener) {
        final ArrayList<SanPham> sanPhams = new ArrayList();
        final String str = key;
        final SearchType searchType2 = searchType;
        final DatabaseListener databaseListener = listener;
        AsyncTask asyncTask = new AsyncTask() {
            protected Object doInBackground(Object[] objects) {
                StringBuilder stringBuilder = new StringBuilder("SELECT * FROM kho WHERE trang_thai = 'active' ");
                if (str != null && searchType2 != null && searchType2.value != SearchType.TYPE_ALL) {
                    stringBuilder.append("AND ");
                    if (searchType2.value == SearchType.TYPE_MA) {
                        stringBuilder.append("ma LIKE '%").append(str).append("%'");
                    } else if (searchType2.value == SearchType.TYPE_TEN) {
                        stringBuilder.append("ten LIKE '%").append(str).append("%'");
                    } else {
                        long[] jArr = new long[2];
                        jArr = new long[]{0, 0};
                        if (searchType2.value == SearchType.TYPE_NGAY) {
                            jArr = SystemTime.getInstance().getLimDayNow();
                        } else if (searchType2.value == SearchType.TYPE_TUAN) {
                            jArr = SystemTime.getInstance().getLimWeekNow();
                        } else if (searchType2.value == SearchType.TYPE_THANG) {
                            jArr = SystemTime.getInstance().getLimMonthNow();
                        } else if (searchType2.value == SearchType.TYPE_NAM) {
                            jArr = SystemTime.getInstance().getLimYearNow();
                        }
                        stringBuilder.append("( thoi_gian > ").append(jArr[0]).append(" AND thoi_gian < ").append(jArr[1]).append(" ) AND ( ma LIKE '%").append(str).append("%' OR ten LIKE '%").append(str).append("%')");
                    }
                } else if (!(str == null || str.equals(BuildConfig.FLAVOR))) {
                    stringBuilder.append("AND ( ma LIKE '%").append(str).append("%' OR ten LIKE '%").append(str).append("%')");
                }
                stringBuilder.append(" ORDER BY id DESC;");
                Log.d("TTT", "sql: " + stringBuilder.toString());
                Cursor cursor = Database.this.sqLiteDatabase.rawQuery(stringBuilder.toString(), null);
                int stt = 0;
                while (cursor.moveToNext()) {
                    stt++;
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String ma = cursor.getString(cursor.getColumnIndex("ma"));
                    String ten = cursor.getString(cursor.getColumnIndex("ten"));
                    double slNhap = cursor.getDouble(cursor.getColumnIndex("sl_nhap"));
                    double slTon = cursor.getDouble(cursor.getColumnIndex("sl_ton"));
                    double giaDX = cursor.getDouble(cursor.getColumnIndex("gia_dx"));
                    double giaNhap = cursor.getDouble(cursor.getColumnIndex("gia_nhap"));
                    sanPhams.add(new SanPham(stt, id, ma, ten, slNhap, slTon, giaNhap, giaDX));
                }
                return null;
            }

            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                databaseListener.callback(new Object[]{sanPhams});
            }
        };
        asyncTask.execute(new Object[0]);
        return asyncTask;
    }


    public AsyncTask getSPKho(String key, SearchType searchType, final String sortType, DatabaseListener listener) {
        final ArrayList<SanPham> sanPhams = new ArrayList();
        final String str = key;
        final SearchType searchType2 = searchType;
        final DatabaseListener databaseListener = listener;
        AsyncTask asyncTask = new AsyncTask() {
            protected Object doInBackground(Object[] objects) {
                StringBuilder stringBuilder = new StringBuilder("SELECT * FROM kho WHERE trang_thai = 'active' ");
                if (str != null && searchType2 != null && searchType2.value != SearchType.TYPE_ALL) {
                    stringBuilder.append("AND ");
                    if (searchType2.value == SearchType.TYPE_MA) {
                        stringBuilder.append("ma LIKE '%").append(str).append("%'");
                    } else if (searchType2.value == SearchType.TYPE_TEN) {
                        stringBuilder.append("ten LIKE '%").append(str).append("%'");
                    } else {
                        long[] jArr = new long[2];
                        jArr = new long[]{0, 0};
                        if (searchType2.value == SearchType.TYPE_NGAY) {
                            jArr = SystemTime.getInstance().getLimDayNow();
                        } else if (searchType2.value == SearchType.TYPE_TUAN) {
                            jArr = SystemTime.getInstance().getLimWeekNow();
                        } else if (searchType2.value == SearchType.TYPE_THANG) {
                            jArr = SystemTime.getInstance().getLimMonthNow();
                        } else if (searchType2.value == SearchType.TYPE_NAM) {
                            jArr = SystemTime.getInstance().getLimYearNow();
                        }
                        stringBuilder.append("( thoi_gian > ").append(jArr[0]).append(" AND thoi_gian < ").append(jArr[1]).append(" ) AND ( ma LIKE '%").append(str).append("%' OR ten LIKE '%").append(str).append("%')");
                    }
                } else if (!(str == null || str.equals(BuildConfig.FLAVOR))) {
                    stringBuilder.append("AND ( ma LIKE '%").append(str).append("%' OR ten LIKE '%").append(str).append("%')");
                }
                stringBuilder.append(sortType);
                Log.d("TTT", "sql: " + stringBuilder.toString());
                Cursor cursor = Database.this.sqLiteDatabase.rawQuery(stringBuilder.toString(), null);
                int stt = 0;
                while (cursor.moveToNext()) {
                    stt++;
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String ma = cursor.getString(cursor.getColumnIndex("ma"));
                    String ten = cursor.getString(cursor.getColumnIndex("ten"));
                    double slNhap = cursor.getDouble(cursor.getColumnIndex("sl_nhap"));
                    double slTon = cursor.getDouble(cursor.getColumnIndex("sl_ton"));
                    double giaDX = cursor.getDouble(cursor.getColumnIndex("gia_dx"));
                    double giaNhap = cursor.getDouble(cursor.getColumnIndex("gia_nhap"));
                    sanPhams.add(new SanPham(stt, id, ma, ten, slNhap, slTon, giaNhap, giaDX));
                }
                return null;
            }

            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                databaseListener.callback(new Object[]{sanPhams});
            }
        };
        asyncTask.execute(new Object[0]);
        return asyncTask;
    }


    public AsyncTask getSPDaBan(String key, SearchType searchType, DatabaseListener listener) {
        final ArrayList<SanPham> sanPhams = new ArrayList();
        final String str = key;
        final SearchType searchType2 = searchType;
        final DatabaseListener databaseListener = listener;
        AsyncTask asyncTask = new AsyncTask() {
            protected Object doInBackground(Object[] objects) {
                StringBuilder stSQL = new StringBuilder("SELECT tb1.*, tb2.ten FROM da_ban as tb1 INNER JOIN kho as tb2 ON tb1.ma = tb2.ma");
                if (str != null && searchType2 != null && searchType2.value != SearchType.TYPE_ALL) {
                    stSQL.append(" WHERE ");
                    if (searchType2.value == SearchType.TYPE_MA) {
                        stSQL.append("tb1.ma LIKE '%").append(str).append("%'");
                    } else if (searchType2.value == SearchType.TYPE_TEN) {
                        stSQL.append("tb2.ten LIKE '%").append(str).append("%'");
                    } else {
                        long[] lim = new long[]{0, 0};
                        if (searchType2.value == SearchType.TYPE_NGAY) {
                            lim = SystemTime.getInstance().getLimDayNow();
                        } else if (searchType2.value == SearchType.TYPE_TUAN) {
                            lim = SystemTime.getInstance().getLimWeekNow();
                        } else if (searchType2.value == SearchType.TYPE_THANG) {
                            lim = SystemTime.getInstance().getLimMonthNow();
                        } else if (searchType2.value == SearchType.TYPE_NAM) {
                            lim = SystemTime.getInstance().getLimYearNow();
                        }
                        stSQL.append("( tb1.thoi_gian > ").append(lim[0]).append(" AND tb1.thoi_gian < ").append(lim[1]).append(" ) AND ( tb1.ma LIKE '%").append(str).append("%' OR tb2.ten LIKE '%").append(str).append("%')");
                    }
                } else if (!(str == null || str.equals(BuildConfig.FLAVOR))) {
                    stSQL.append(" WHERE tb1.ma LIKE '%").append(str).append("%' OR tb2.ten LIKE '%").append(str).append("%'");
                }
                stSQL.append(" ORDER BY tb1.thoi_gian DESC;");
                Log.d("TTT", "sql: " + stSQL.toString());
                Cursor cursor = Database.this.sqLiteDatabase.rawQuery(stSQL.toString(), null);
                int stt = 0;
                while (cursor.moveToNext()) {
                    stt++;
                    sanPhams.add(new SanPham(stt, cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("ma")), cursor.getString(cursor.getColumnIndex("ten")), cursor.getDouble(cursor.getColumnIndex("gia_ban_ra")), cursor.getDouble(cursor.getColumnIndex("sl")), SystemTime.getInstance().parseTime(cursor.getLong(cursor.getColumnIndex("thoi_gian")))));
                }
                return null;
            }

            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                databaseListener.callback(new Object[]{sanPhams});
            }
        };
        asyncTask.execute(new Object[0]);
        return asyncTask;
    }

    public AsyncTask getThuChi(final String key, final SearchType searchType, final String sortType, final DatabaseListener listener) {
        AsyncTask<Void, Void, Object[]> asyncTask = new AsyncTask<Void, Void, Object[]>() {
            protected Object[] doInBackground(Void... voids) {
                ArrayList<SanPham> sanPhams = new ArrayList();
                double tongThuVe = 0;
                double tongLai = 0;
                double tongVon = 0;
                StringBuilder stringBuilder = new StringBuilder("SELECT tb1.id, tb1.ma, tb2.ten, sum(sl) as 'sl_ban_ra', sum(tb1.gia_ban_ra * tb1.sl) as 'tong_thu',sum((tb1.gia_ban_ra - tb1.gia_nhap) * tb1.sl) as 'lai', sum(tb1.gia_nhap * tb1.sl) as 'von' FROM da_ban as tb1 INNER JOIN kho as tb2 ON tb1.ma = tb2.ma");
                if (key != null && searchType != null && searchType.value != SearchType.TYPE_ALL) {
                    stringBuilder.append(" WHERE ");
                    if (searchType.value == SearchType.TYPE_MA) {
                        stringBuilder.append("tb1.ma LIKE '%").append(key).append("%'");
                    } else if (searchType.value == SearchType.TYPE_TEN) {
                        stringBuilder.append("tb2.ten LIKE '%").append(key).append("%'");
                    } else {
                        long[] jArr = new long[2];
                        jArr = new long[]{0, 0};
                        if (searchType.value == SearchType.TYPE_NGAY) {
                            jArr = SystemTime.getInstance().getLimDayNow();
                        } else if (searchType.value == SearchType.TYPE_TUAN) {
                            jArr = SystemTime.getInstance().getLimWeekNow();
                        } else if (searchType.value == SearchType.TYPE_THANG) {
                            jArr = SystemTime.getInstance().getLimMonthNow();
                        } else if (searchType.value == SearchType.TYPE_NAM) {
                            jArr = SystemTime.getInstance().getLimYearNow();
                        }
                        stringBuilder.append("( tb1.thoi_gian > ").append(jArr[0]).append(" AND tb1.thoi_gian < ").append(jArr[1]).append(" ) AND ( tb1.ma LIKE '%").append(key).append("%' OR tb2.ten LIKE '%").append(key).append("%')");
                    }
                } else if (!(key == null || key.equals(BuildConfig.FLAVOR))) {
                    stringBuilder.append(" WHERE tb1.ma LIKE '%").append(key).append("%' OR tb2.ten LIKE '%").append(key).append("%'");
                }
                stringBuilder.append(" GROUP BY tb1.ma ");
                stringBuilder.append(sortType);
                Log.d("TTT", "sql: " + stringBuilder.toString());
                Cursor cursor = Database.this.sqLiteDatabase.rawQuery(stringBuilder.toString(), null);
                int stt = 0;
                while (cursor.moveToNext()) {
                    stt++;
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String ma = cursor.getString(cursor.getColumnIndex("ma"));
                    String ten = cursor.getString(cursor.getColumnIndex("ten"));
                    double slBanRa = cursor.getDouble(cursor.getColumnIndex("sl_ban_ra"));
                    double tongThu = cursor.getDouble(cursor.getColumnIndex("tong_thu"));
                    double lai = cursor.getDouble(cursor.getColumnIndex("lai"));
                    double von = cursor.getDouble(cursor.getColumnIndex("von"));
                    tongThuVe += tongThu;
                    tongLai += lai;
                    tongVon += von;
                    sanPhams.add(new SanPham(stt, id, ma, ten, slBanRa, tongThu, lai, von, BuildConfig.FLAVOR));
                }
                return new Object[]{sanPhams, Double.valueOf(tongThuVe), Double.valueOf(tongLai), Double.valueOf(tongVon)};
            }

            protected void onPostExecute(Object[] objects) {
                super.onPostExecute(objects);
                listener.callback(objects);
            }
        };
        asyncTask.execute(new Void[0]);
        return asyncTask;
    }


    public int count(String tableName) {
        Cursor cursor = this.sqLiteDatabase.rawQuery("SELECT count(*) as 'count' FROM " + tableName + ";", null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("count"));
        }
        return -1;
    }

    public int countDaBan() {
        return count("da_ban");
    }

    public int countKho() {
        Cursor cursor = this.sqLiteDatabase.rawQuery(new StringBuilder("SELECT count(*) as 'count' FROM kho WHERE trang_thai = 'active';").toString(), null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("count"));
        }
        return -1;
    }

    public void exportDatabse() {

        try {

            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.android.salesmanager//databases//database.db";
                String backupDBPath = "attendant_info_backup.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Log.d("TTT","dg");
                }
            }

        } catch (Exception e) {
            Log.d("TTT","dg"+e);
        }


    }
    public void importDB() {

        try {
            File sd = Environment.getExternalStorageDirectory().getAbsoluteFile();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "com.android.salesmanager"
                        + "//databases//" + "database.db";
                String backupDBPath = "database.db"; // From SD directory.
                File backupDB = new File(sd, backupDBPath);
                File currentDB = new File(data, currentDBPath);

                FileChannel src = new FileInputStream(backupDB).getChannel();
                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
            Log.d("TTT","sffg");
        }
    }

    public void exportDB() {
        try {
            File sd = Environment.getExternalStorageDirectory().getAbsoluteFile();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "com.android.salesmanager"
                        + "//databases//" + "database.db";
                String backupDBPath = "database.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
        }
    }
}