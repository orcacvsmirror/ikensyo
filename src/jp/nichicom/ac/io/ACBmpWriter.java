package jp.nichicom.ac.io;

import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import jp.nichicom.ac.ACCommon;

/**
 * BMP�o�̓N���X�ł��B(�o�͂���BMP��16�F�ł�)
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
     * �R���X�g���N�^�ł��B
     *
     * @param file �o�͐�t�@�C��
     * @param img �C���[�W
     * @param width �C���[�W��
     * @param height �C���[�W����
     */
    public ACBmpWriter(File file, Image img, int width, int height) {
        this.file = file;
        this.width = width;
        this.height = height;
        pix = new int[width * height];
        PixelGrabber pg = new PixelGrabber(img, 0, 0, width, height, pix, 0,
                width);
        try {
            pg.grabPixels(); // �z��ɃC���[�W���i�[�����̂�҂�
        } catch (InterruptedException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * �o�͂��܂��B
     *
     * @throws IOException ������O
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
     * ���������܂��B
     */
    private void initialize() {
        rgbQuadCnt = 16;
        biBitCount = 4;
        biClrUsed = 0;

        length = ((width * biBitCount + 31) & ~31) >>> 3;
        sizeOfRGBQuad = rgbQuadCnt << 2;
    }

    /**
     * �r�b�g�}�b�v�t�@�C���w�b�_���o�͂��܂��B
     *
     * @throws Exception ������O
     */
    private void writeBitmapFileHeader() throws Exception {
        // typedef struct tagBITMAPFILEHEADER { // bmfh
        // WORD bfType; //BM
        // DWORD bfSize; //�t�@�C���̃T�C�Y
        // WORD bfReserved1;
        // WORD bfReserved2;
        // DWORD bfOffBits; //�t�@�C���̐擪����r�b�g�z��܂ł̃I�t�Z�b�g�l
        // } BITMAPFILEHEADER;

        ByteBuffer bmfh = new ByteBuffer(SIZE_OF_BITMAPFILEHEADER);
        // WORD bfType:BM
        bmfh.addByte((byte) 'B');
        bmfh.addByte((byte) 'M');
        // DWORD bfSize:�t�@�C���̃T�C�Y
        bmfh.addDWORD(SIZE_OF_BITMAPFILEHEADER + SIZE_OF_BITMAPINFOHEADER
                + sizeOfRGBQuad + length * height);
        // WORD bfReserved1
        bmfh.addWORD(0);
        // WORD bfReserved2
        bmfh.addWORD(0);
        // DWORD bfOffBits:�t�@�C���̐擪����r�b�g�z��܂ł̃I�t�Z�b�g�l
        bmfh.addDWORD(SIZE_OF_BITMAPFILEHEADER + SIZE_OF_BITMAPINFOHEADER
                + sizeOfRGBQuad);

        fos.write(bmfh.getBytes());
    }

    /**
     * �r�b�g�}�b�v���w�b�_���o�͂��܂��B
     *
     * @throws Exception ������O
     */
    private void writeBitmapInfoHeader() throws Exception {
        // typedef struct tagBITMAPINFOHEADER{ // bmih
        // DWORD biSize; //�\���̂̑傫��
        // LONG biWidth; //�r�b�g�}�b�v�̕�
        // LONG biHeight; //�r�b�g�}�b�v�̍���
        // WORD biPlanes; //�v���[���̐�(1)
        // WORD biBitCount; //�s�N�Z��������̐F��(1,4,8,24�̂����ꂩ)
        // DWORD biCompression; //���k����(0�ň��k�Ȃ�)
        // DWORD biSizeImage; //�r�b�g�}�b�v�r�b�g�̃T�C�Y(���k�̎��̂ݕK�v)
        // LONG biXPelsPerMeter; //�����𑜓x
        // LONG biYPelsPerMeter; //�����𑜓x
        // DWORD biClrUsed; //�C���[�W�Ŏg���Ă���F��
        // DWORD biClrImportant; //�C���[�W�Ŏg���Ă���d�v�ȐF�̐�
        // } BITMAPINFOHEADER;
        ByteBuffer bmih = new ByteBuffer(SIZE_OF_BITMAPINFOHEADER);
        // DWORD biSize:�\���̂̑傫��
        bmih.addDWORD(SIZE_OF_BITMAPINFOHEADER);
        // LONG biWidth:�r�b�g�}�b�v�̕�
        bmih.addLong(width);
        // LONG biHeight:�r�b�g�}�b�v�̍���
        bmih.addLong(height);
        // WORD biPlanes:�v���[���̐�(1)
        bmih.addWORD(1);
        // WORD biBitCount:�s�N�Z��������̐F��(1,4,8,24�̂����ꂩ)
        bmih.addWORD(4);
        // DWORD biCompression:���k����(0�ň��k�Ȃ�)
        bmih.addDWORD(0);
        // DWORD biSizeImage:�r�b�g�}�b�v�r�b�g�̃T�C�Y(���k�̎��̂ݕK�v)
        bmih.addDWORD(length * height);
        // LONG biXPelsPerMeter:�����𑜓x
        bmih.addLong(0);
        // LONG biYPelsPerMeter:�����𑜓x
        bmih.addLong(0);
        // DWORD biClrUsed:�C���[�W�Ŏg���Ă���F��
        bmih.addDWORD(biClrUsed);
        // DWORD biClrImportant:�C���[�W�Ŏg���Ă���d�v�ȐF�̐�
        bmih.addDWORD(0);

        fos.write(bmih.getBytes());
    }

    /**
     * RGBQUAD�\���̂��o�͂��܂��B
     *
     * @throws Exception ������O
     */
    private void writeRGBQuad() throws Exception {
        // biClrUsed=0
        //     biBitCount��1:2��RGBQUAD�\����
        //                 4:16��RGBQUAD�\����
        //                 8:256��RGBQUAD�\����
        //                   (FullColor����RGBQUAD�\���̂͂Ȃ�)
        // biClrUsed!=0
        //     ���̐�����RGBQUAD�\���̂�����
        //
        // typedef struct tagRGBQUAD { // rgbq
        //     BYTE rgbBlue; //��
        //     BYTE rgbGreen; //��
        //     BYTE rgbRed; //��
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
    // * �i���g�p�j
    // * @param size int
    // * @return int[]
    // */
    // private int[] getPallet(int size) {
    // int[] cols = new int[size];
    // int colCnt = 0;
    //
    // //�p���b�g������
    // for (int i=0; i<cols.length; i++) {
    // cols[i] = 0;
    // }
    //
    // for (int i=0; i<pix.length; i++) {
    // //�p���b�g�Ɋ����̐F�ł������ꍇ�A���̃s�N�Z���ցB
    // boolean addFlg = true;
    // for (int j = 0; j < cols.length; j++) {
    // if (cols[j] == pix[i]) {
    // addFlg = false;
    // break;
    // }
    // }
    // //�p���b�g�ɒǉ�
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
     * �p���b�g�����쐬���܂��B
     *
     * @param size �p���b�g�T�C�Y
     * @return �p���b�g
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
     * �f�[�^�{�̂��o�͂��܂��B(16�FBMP)
     *
     * @throws Exception ������O
     */
    private void writeBody() throws Exception {
        // RGBQUAD�\���̂̌�Ƀr�b�g�}�b�v�f�[�^�������B
        // ������E�ɁA�������Ƀf�[�^�������Ă���B

        ByteBuffer b = new ByteBuffer(height * length);
        int off = ((4 - width % 4) % 4) / 2;

        // pixel�f�[�^����
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
     * �w��F�ƕ�������p���b�g�̃C���f�b�N�X��Ԃ��܂��B
     * <p>
     * ���S��v����F��������΁A��ԋ߂��F�̃C���f�b�N�X��Ԃ��܂��B
     * </p>
     *
     * @param color int
     * @return int
     */
    private int getPalletIndex(int color) {
        // ���S��v
        for (int i = 0; i < pallet.length; i++) {
            if (pallet[i] == color) {
                return i;
            }
        }

        // �߂����̂�T��(�P���ɁAR-G-B��3������ԓ��ŋ�������ԋ߂����̂�T��)

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
     * RGBQuad�\���̂ł��B
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
     * �o�b�t�@�N���X�ł��B
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
