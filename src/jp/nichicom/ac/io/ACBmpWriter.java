package jp.nichicom.ac.io;

import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import jp.nichicom.ac.ACCommon;

/**
 * BMP出力クラスです。(出力するBMPは16色です)
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 *
 * @author Mizuki Tsutsumi
 * @version 1.0 2005/12/01
 */
public class ACBmpWriter {
    private File file;
    private int[] pix;
    private int[] pallet;
    private FileOutputStream fos;
    private final int SIZE_OF_BITMAPFILEHEADER = 14;
    private final int SIZE_OF_BITMAPINFOHEADER = 40;
    private int sizeOfRGBQuad;
    private int width;
    private int height;
    private int length;
    private int rgbQuadCnt;
    private int biBitCount;
    private int biClrUsed;

    /**
     * コンストラクタです。
     *
     * @param file 出力先ファイル
     * @param img イメージ
     * @param width イメージ幅
     * @param height イメージ高さ
     */
    public ACBmpWriter(File file, Image img, int width, int height) {
        this.file = file;
        this.width = width;
        this.height = height;
        pix = new int[width * height];
        PixelGrabber pg = new PixelGrabber(img, 0, 0, width, height, pix, 0,
                width);
        try {
            pg.grabPixels(); // 配列にイメージが格納されるのを待つ
        } catch (InterruptedException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * 出力します。
     *
     * @throws IOException 処理例外
     */
    public void write() throws IOException {
        try {
            initialize();
            fos = new FileOutputStream(file);
            writeBitmapFileHeader();
            writeBitmapInfoHeader();
            writeRGBQuad();
            writeBody();
            fos.close();
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        } catch (Exception ex) {
            ACCommon.getInstance().showExceptionMessage(ex);
        }
    }

    /**
     * 初期化します。
     */
    private void initialize() {
        rgbQuadCnt = 16;
        biBitCount = 4;
        biClrUsed = 0;

        length = ((width * biBitCount + 31) & ~31) >>> 3;
        sizeOfRGBQuad = rgbQuadCnt << 2;
    }

    /**
     * ビットマップファイルヘッダを出力します。
     *
     * @throws Exception 処理例外
     */
    private void writeBitmapFileHeader() throws Exception {
        // typedef struct tagBITMAPFILEHEADER { // bmfh
        // WORD bfType; //BM
        // DWORD bfSize; //ファイルのサイズ
        // WORD bfReserved1;
        // WORD bfReserved2;
        // DWORD bfOffBits; //ファイルの先頭からビット配列までのオフセット値
        // } BITMAPFILEHEADER;

        ByteBuffer bmfh = new ByteBuffer(SIZE_OF_BITMAPFILEHEADER);
        // WORD bfType:BM
        bmfh.addByte((byte) 'B');
        bmfh.addByte((byte) 'M');
        // DWORD bfSize:ファイルのサイズ
        bmfh.addDWORD(SIZE_OF_BITMAPFILEHEADER + SIZE_OF_BITMAPINFOHEADER
                + sizeOfRGBQuad + length * height);
        // WORD bfReserved1
        bmfh.addWORD(0);
        // WORD bfReserved2
        bmfh.addWORD(0);
        // DWORD bfOffBits:ファイルの先頭からビット配列までのオフセット値
        bmfh.addDWORD(SIZE_OF_BITMAPFILEHEADER + SIZE_OF_BITMAPINFOHEADER
                + sizeOfRGBQuad);

        fos.write(bmfh.getBytes());
    }

    /**
     * ビットマップ情報ヘッダを出力します。
     *
     * @throws Exception 処理例外
     */
    private void writeBitmapInfoHeader() throws Exception {
        // typedef struct tagBITMAPINFOHEADER{ // bmih
        // DWORD biSize; //構造体の大きさ
        // LONG biWidth; //ビットマップの幅
        // LONG biHeight; //ビットマップの高さ
        // WORD biPlanes; //プレーンの数(1)
        // WORD biBitCount; //ピクセルあたりの色数(1,4,8,24のいずれか)
        // DWORD biCompression; //圧縮方式(0で圧縮なし)
        // DWORD biSizeImage; //ビットマップビットのサイズ(圧縮の時のみ必要)
        // LONG biXPelsPerMeter; //水平解像度
        // LONG biYPelsPerMeter; //垂直解像度
        // DWORD biClrUsed; //イメージで使われている色数
        // DWORD biClrImportant; //イメージで使われている重要な色の数
        // } BITMAPINFOHEADER;
        ByteBuffer bmih = new ByteBuffer(SIZE_OF_BITMAPINFOHEADER);
        // DWORD biSize:構造体の大きさ
        bmih.addDWORD(SIZE_OF_BITMAPINFOHEADER);
        // LONG biWidth:ビットマップの幅
        bmih.addLong(width);
        // LONG biHeight:ビットマップの高さ
        bmih.addLong(height);
        // WORD biPlanes:プレーンの数(1)
        bmih.addWORD(1);
        // WORD biBitCount:ピクセルあたりの色数(1,4,8,24のいずれか)
        bmih.addWORD(4);
        // DWORD biCompression:圧縮方式(0で圧縮なし)
        bmih.addDWORD(0);
        // DWORD biSizeImage:ビットマップビットのサイズ(圧縮の時のみ必要)
        bmih.addDWORD(length * height);
        // LONG biXPelsPerMeter:水平解像度
        bmih.addLong(0);
        // LONG biYPelsPerMeter:垂直解像度
        bmih.addLong(0);
        // DWORD biClrUsed:イメージで使われている色数
        bmih.addDWORD(biClrUsed);
        // DWORD biClrImportant:イメージで使われている重要な色の数
        bmih.addDWORD(0);

        fos.write(bmih.getBytes());
    }

    /**
     * RGBQUAD構造体を出力します。
     *
     * @throws Exception 処理例外
     */
    private void writeRGBQuad() throws Exception {
        // biClrUsed=0
        //     biBitCountが1:2個のRGBQUAD構造体
        //                 4:16個のRGBQUAD構造体
        //                 8:256個のRGBQUAD構造体
        //                   (FullColor時はRGBQUAD構造体はなし)
        // biClrUsed!=0
        //     その数だけRGBQUAD構造体がくる
        //
        // typedef struct tagRGBQUAD { // rgbq
        //     BYTE rgbBlue; //青
        //     BYTE rgbGreen; //緑
        //     BYTE rgbRed; //赤
        //     BYTE rgbReserved;
        // } RGBQUAD;

        ByteBuffer rgbQuad = new ByteBuffer(rgbQuadCnt * 4);
        pallet = createPallet(rgbQuadCnt);
        for (int i = 0; i < rgbQuadCnt; i++) {
            RGBQuad rgbq = new RGBQuad(pallet[i]);
            rgbQuad.addByte((byte) rgbq.getB());
            rgbQuad.addByte((byte) rgbq.getG());
            rgbQuad.addByte((byte) rgbq.getR());
            rgbQuad.addByte((byte) rgbq.getReserved());
        }

        fos.write(rgbQuad.getBytes());
    }

    // /**
    // * （未使用）
    // * @param size int
    // * @return int[]
    // */
    // private int[] getPallet(int size) {
    // int[] cols = new int[size];
    // int colCnt = 0;
    //
    // //パレット初期化
    // for (int i=0; i<cols.length; i++) {
    // cols[i] = 0;
    // }
    //
    // for (int i=0; i<pix.length; i++) {
    // //パレットに既存の色であった場合、次のピクセルへ。
    // boolean addFlg = true;
    // for (int j = 0; j < cols.length; j++) {
    // if (cols[j] == pix[i]) {
    // addFlg = false;
    // break;
    // }
    // }
    // //パレットに追加
    // if (addFlg) {
    // if (colCnt < cols.length) {
    // cols[colCnt] = pix[i];
    // colCnt++;
    // }
    // }
    // }
    //
    // return cols;
    // }

    /**
     * パレット情報を作成します。
     *
     * @param size パレットサイズ
     * @return パレット
     */
    private int[] createPallet(int size) {
        int cols[] = new int[size];
        for (int i = 0; i < size; i++) {
            int c = i * 256 / 15;
            if (c > 255) {
                c = 255;
            }
            cols[i] = (c << 8 | c) << 8 | c;
        }

        // int r,g,b;
        // r= 0;g= 0;b= 0; cols[ 0]=(b<<8|g)<<8|r;
        // r=128;g= 0;b= 0; cols[ 1]=(b<<8|g)<<8|r;
        // r= 0;g=128;b= 0; cols[ 2]=(b<<8|g)<<8|r;
        // r=128;g=128;b= 0; cols[ 3]=(b<<8|g)<<8|r;
        // r= 0;g= 0;b=128; cols[ 4]=(b<<8|g)<<8|r;
        // r=128;g= 0;b=128; cols[ 5]=(b<<8|g)<<8|r;
        // r= 0;g=128;b=128; cols[ 6]=(b<<8|g)<<8|r;
        // r=128;g=128;b=128; cols[ 7]=(b<<8|g)<<8|r;
        // r=192;g=192;b=192; cols[ 8]=(b<<8|g)<<8|r;
        // r=255;g= 0;b= 0; cols[ 9]=(b<<8|g)<<8|r;
        // r= 0;g=255;b= 0; cols[10]=(b<<8|g)<<8|r;
        // r=255;g=255;b= 0; cols[11]=(b<<8|g)<<8|r;
        // r= 0;g= 0;b=255; cols[12]=(b<<8|g)<<8|r;
        // r=255;g= 0;b=255; cols[13]=(b<<8|g)<<8|r;
        // r= 0;g=255;b=255; cols[14]=(b<<8|g)<<8|r;
        // r=255;g=255;b=255; cols[15]=(b<<8|g)<<8|r;

        return cols;
    }

    /**
     * データ本体を出力します。(16色BMP)
     *
     * @throws Exception 処理例外
     */
    private void writeBody() throws Exception {
        // RGBQUAD構造体の後にビットマップデータが続く。
        // 左から右に、下から上にデータが入っている。

        ByteBuffer b = new ByteBuffer(height * length);
        int off = ((4 - width % 4) % 4) / 2;

        // pixelデータ生成
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x += 2) {
                b.addByte((byte) (getPalletIndex(pix[y * width + x]) << 4 | (getPalletIndex(pix[y
                                * width + x + 1]))));
            }
            for (int i = 0; i < off; i++) {
                b.addByte((byte) 0);
            }
        }
        fos.write(b.getBytes());
    }

    /**
     * 指定色と符合するパレットのインデックスを返します。
     * <p>
     * 完全一致する色が無ければ、一番近い色のインデックスを返します。
     * </p>
     *
     * @param color int
     * @return int
     */
    private int getPalletIndex(int color) {
        // 完全一致
        for (int i = 0; i < pallet.length; i++) {
            if (pallet[i] == color) {
                return i;
            }
        }

        // 近いものを探す(単純に、R-G-Bの3次元空間内で距離が一番近いものを探す)

        // RGBQuad rgbPix = new RGBQuad(color);
        // if (rgbPix.getR() + rgbPix.getG() + rgbPix.getB() == 255 * 3) {
        // return 15;
        // }
        // else {
        // return 0;
        // }

        int dist = Integer.MAX_VALUE;
        int idx = 0;
        for (int i = 0; i < pallet.length; i++) {
            RGBQuad rgbPix = new RGBQuad(color);
            RGBQuad rgbPal = new RGBQuad(pallet[i]);
            int distTmp = (rgbPix.getR() - rgbPal.getR())
                    * (rgbPix.getR() - rgbPal.getR())
                    + (rgbPix.getG() - rgbPal.getG())
                    * (rgbPix.getG() - rgbPal.getG())
                    + (rgbPix.getB() - rgbPal.getB())
                    * (rgbPix.getB() - rgbPal.getB());
            if (distTmp < dist) {
                dist = distTmp;
                idx = i;
            }
        }
        return idx;
    }

    /**
     * RGBQuad構造体です。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
     * </p>
     *
     * @author Mizuki Tsutsumi
     * @version 1.0 2005/12/01
     */
    private class RGBQuad {
        int rgbRed, rgbGreen, rgbBlue, rgbReserved;

        public RGBQuad(int color) {
            int c = color & 0xffffff;
            this.rgbRed = c >> 16;
            this.rgbGreen = (c >> 8) & 0xff;
            this.rgbBlue = c & 0xff;
            this.rgbReserved = 0;
        }

        public int getR() {
            return rgbRed;
        }

        public int getG() {
            return rgbGreen;
        }

        public int getB() {
            return rgbBlue;
        }

        public int getReserved() {
            return rgbReserved;
        }
    }

    /**
     * バッファクラスです。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
     * </p>
     *
     * @author Mizuki Tsutsumi
     * @version 1.0 2005/12/01
     */
    private class ByteBuffer {
        private byte[] bytes;
        private int idx;

        ByteBuffer(int len) {
            bytes = new byte[len];
            idx = 0;
        }

        public byte[] getBytes() {
            return bytes;
        }

        public void setIndex(int idx) {
            this.idx = idx;
        }

        public void setByte(byte b, int idx) {
            bytes[idx] = b;
        }

        public void setWORD(int word, int idx) {
            bytes[idx + 1] = (byte) (word >> 8);
            bytes[idx] = (byte) word;
        }

        public void setDWORD(int dword, int idx) {
            setWORD(dword >> 16, idx + 2);
            setWORD(dword, idx);
        }

        public void setLong(long lng, int idx) {
            setDWORD((int) lng, idx);
        }

        public void addByte(byte b) {
            setByte(b, this.idx);
            this.idx++;
        }

        public void addWORD(int word) {
            setWORD(word, this.idx);
            this.idx += 2;
        }

        public void addDWORD(int dword) {
            setDWORD(dword, this.idx);
            this.idx += 4;
        }

        public void addLong(long lng) {
            addDWORD((int) lng);
        }
    }
}
