package jp.or.med.orca.ikensho.pdf.ip.ip001;

import java.io.File;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * 帳票定義体IDを変換するフォーマットです。<br>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Shin Fujihara
 * @version 1.0 2005/12/07
 * @see Format
 */

public class IP001FormatIDFormat extends Format {

    private static IP001FormatIDFormat singleton;
    protected int formatType = -1;

    /**
     * 帳票形式 を返します。
     * 
     * @return 帳票形式
     */
    public int getFormatType() {
        return formatType;
    }

    /**
     * 帳票形式 を設定します。
     * 
     * @param formatType 帳票形式
     */
    public void setFormatType(int formatType) {
        this.formatType = formatType;
    }

    /**
     * 対象のファイル を設定します。
     * 
     * @param targetFile 対象のファイル
     */
    public void setTargetFile(File targetFile) {
        int newID = -1;
        if (targetFile != null) {
            String fileName = targetFile.getName().toLowerCase();
            if (fileName.endsWith("newikensho1.xml")) {
                newID = 0;
            } else if (fileName.endsWith("newikensho2.xml")) {
                newID = 1;
//              [ID:0000514][Tozo TANAKA] 2009/09/09 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
            } else if ("specialshijisho.xml".equals(fileName)) {
                newID = 13;
//              [ID:0000514][Tozo TANAKA] 2009/09/09 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能 
            } else if (fileName.endsWith("shijisho.xml")) {
                newID = 2;
            } else if (fileName.endsWith("shijishob.xml")) {
                newID = 3;
            } else if (fileName.endsWith("seikyuichiran.xml")) {
                newID = 4;
            } else if (fileName.endsWith("seikyuichirantotal.xml")) {
                newID = 5;
            } else if (fileName.endsWith("soukatusho.xml")) {
                newID = 6;
            } else if (fileName.endsWith("ikenshomeisai.xml")) {
                newID = 7;
            } else if (fileName.endsWith("csvfileoutputpatientlist.xml")) {
                newID = 10;
            } else if (fileName.endsWith("patientlist.xml")) {
                newID = 8;
            } else if (fileName.endsWith("seikyuikenshoichiran.xml")) {
                newID = 9;
            } else if (fileName.endsWith("ikenshoshien1.xml")) {
                newID = 11;
            } else if (fileName.endsWith("ikenshoshien2.xml")) {
                newID = 12; 
            }
            //[ID:0000639][Shin Fujihara] 2011/03 add begin
            else if(fileName.equals("shijisho_m1.xml")) {
            	newID = 14;
            } else if(fileName.equals("shijisho_m2.xml")) {
            	newID = 15;
            } else if(fileName.equals("shijishob_m1.xml")) {
            	newID = 16;
            } else if(fileName.equals("shijishob_m2.xml")) {
            	newID = 17;
            }
            //[ID:0000639][Shin Fujihara] 2011/03 add end
        }
        setFormatType(newID);

    }
    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if (obj == null) {
            return toAppendTo;
        }

        String code = String.valueOf(obj);
        switch (getFormatType()) {
        case 0://主治医意見書1ページ目
            obj = formatIkensho1(code, obj);
            break;
        case 1://主治医意見書2ページ目
            obj = formatIkensho2(code, obj);
            break;
        case 2://訪問看護指示書（医療機関）
            obj = formatShijisho(code, obj);
            break;
        case 3://訪問看護指示書（介護老人保健施設）
            obj = formatShijishoB(code, obj);
            break;
        case 4://請求書一覧
            obj = formatSeikyuIchitan(code, obj);
            break;
        case 5://請求書合計
            obj = formatSeikyuIchiranTotal(code, obj);
            break;
        case 6://主治医意見書作成料・検査料請求(総括)書
            obj = formatSoukatsusho(code, obj);
            break;
        case 7://主治医意見書作成料請求(明細)書
            obj = formatMeisaisho(code, obj);
            break;
        case 8://登録患者一覧
            obj = formatPatientList(code, obj);
            break;
        case 9://請求対象意見書一覧
            obj = formatSeikyuIkenshoIchiran(code, obj);
            break;
        case 10://ＣＳＶファイル提出患者一覧
            obj = formatCSVList(code, obj);
            break;
        case 11://医師意見書1ページ目
            obj = formatIkenshoShien1(code, obj);
            break;
        case 12://医師意見書2ページ目
            obj = formatIkenshoShien2(code, obj);
            break;
//          [ID:0000514][Tozo TANAKA] 2009/09/09 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        case 13://特別訪問看護指示書
            obj = formatTokubetsuShijisho(code, obj);
            break;
//          [ID:0000514][Tozo TANAKA] 2009/09/09 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能
        //[ID:0000639][Shin Fujihara] 2011/03 add begin
        case 14: //訪問看護指示書（医療機関）1ページ目
        	obj = formatShijisho_M1(code, obj);
        	break;
        case 15: //訪問看護指示書（医療機関）2ページ目
        	obj = formatShijisho_M2(code, obj);
        	break;
        case 16: //訪問看護指示書（介護老人保健施設）1ページ目
        	obj = formatShijishoB_M1(code, obj);
        	break;
        case 17: //訪問看護指示書（介護老人保健施設）2ページ目
        	obj = formatShijishoB_M2(code, obj);
        	break;
        //[ID:0000639][Shin Fujihara] 2011/03 add end
        }

        toAppendTo.append(obj);

        return toAppendTo;
    }

    /**
     * インスタンスを返します。
     * 
     * @return インスタンス
     */
    public static IP001FormatIDFormat getInstance() {
        if (singleton == null) {
            singleton = new IP001FormatIDFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if (source == null) {
            return "";
        }

        switch (getFormatType()) {
        case 0://主治医意見書1ページ目
            if ("社保".equals(source))
                return "3";
            if ("社保".equals(source))
                return "3";
            if ("社保".equals(source))
                return "3";
            if ("社保".equals(source))
                return "3";
            if ("社保".equals(source))
                return "3";
            break;
            
        case 10://ＣＳＶファイル提出患者一覧
            if ("社保".equals(source))
                return "3";
            if ("社保".equals(source))
                return "3";
            if ("社保".equals(source))
                return "3";
            if ("社保".equals(source))
                return "3";
            if ("社保".equals(source))
                return "3";
            break;
        }

        return "";
    }
    
    /**
     * 意見書1ページ目の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatIkensho1(String code, Object obj){
        if ("Label20".equals(code)) obj = "「主治医意見書」";
        else if ("Grid5.h3.w1".equals(code)) obj = "医療機関・FAX番号　「FAX」";
        else if ("Grid5.h3.w2".equals(code)) obj = "医療機関・FAX番号　FAX番号1";
        else if ("Grid5.h3.w4".equals(code)) obj = "医療機関・FAX番号　FAX番号2";
        else if ("Grid5.h3.w6".equals(code)) obj = "医療機関・FAX番号　FAX番号3";
        else if ("Grid5.h1.w1".equals(code)) obj = "医療機関・電話番号　「電話」";
        else if ("Grid5.h1.w2".equals(code)) obj = "医療機関・電話番号　電話番号1";
        else if ("Grid5.h1.w4".equals(code)) obj = "医療機関・電話番号　電話番号2";
        else if ("Grid5.h1.w6".equals(code)) obj = "医療機関・電話番号　電話番号3";
        else if ("Grid4.h1.w1".equals(code)) obj = "医療機関情報・医師氏名　「医師氏名」";
        else if ("Grid2.h1.w1".equals(code)) obj = "記入日　「記入日」";
        else if ("Grid2.h1.w3".equals(code)) obj = "記入日";
        else if ("Grid6.h1.w1".equals(code)) obj = "最終診察日　「(１) 最終診察日」";
        else if ("Grid13.h2.w2".equals(code)) obj = "症状名　「〔症状名：」";
        else if ("Label9".equals(code)) obj = "傷病に関する意見　「１. 傷病に関する意見」";
        else if ("Label3".equals(code)) obj = "傷病または治療内容";
        else if ("Grid8.h1.w2".equals(code)) obj = "診断名1";
        else if ("Grid8.h2.w2".equals(code)) obj = "診断名2";
        else if ("Grid8.h3.w2".equals(code)) obj = "診断名3";
        else if ("Grid7.h5.w2".equals(code)) obj = "傷病又は治療内容2　「(３) 生活機能低下の直接の原因となっている傷病または特定疾病の経過及び投薬内容を含む治療内容」";
        else if ("Label23".equals(code)) obj = "申請者・生年月日・年号　「明」";
        else if ("Label26".equals(code)) obj = "申請者・性別　「男」";
        else if ("Grid1.h3.w11".equals(code)) obj = "申請者情報　「連絡先」";
        else if ("Grid1.h3.w15".equals(code)) obj = "申請者情報・年齢";
        else if ("Grid7.h1.w2".equals(code)) obj = "診断名及び発症年月日　「(１) 診断名」";
        else if ("Label21".equals(code)) obj = "診断名及び発症年月日　「（特定疾病または生活機能低下の直接の原因となっている傷病名については１.に記入）及び発症年月日」";
        else if ("Grid13.h1.w1".equals(code)) obj = "その他の精神･神経症状　「(４) その他の精神･神経症状」";
        else if ("Grid6.h5.w1".equals(code)) obj = "他科受診の有無　「(３) 他科受診の有無」";
        else if ("Grid9".equals(code)) obj = "投薬内容情報";
        else if ("Grid9.h1.w1".equals(code)) obj = "投薬内容・1行目左";
        else if ("Grid9.h1.w2".equals(code)) obj = "投薬内容・1行目右";
        else if ("Grid9.h2.w1".equals(code)) obj = "投薬内容・2行目左";
        else if ("Grid9.h2.w2".equals(code)) obj = "投薬内容・2行目右";
        else if ("Grid9.h3.w1".equals(code)) obj = "投薬内容・3行目左";
        else if ("Grid9.h3.w2".equals(code)) obj = "投薬内容・3行目右";
        else if ("Grid10.h1.w1".equals(code)) obj = "特別な医療・処置内容　「処置内容」";
        else if ("Label11".equals(code)) obj = "特別な医療1　「２. 特別な医療」";
        else if ("Grid8.h1.w3".equals(code)) obj = "発症年月日1　「発症年月日」";
        else if ("Grid8.h2.w3".equals(code)) obj = "発症年月日2　「発症年月日」";
        else if ("Grid8.h3.w3".equals(code)) obj = "発症年月日3　「発症年月日」";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add begin 【2009年度対応：追加案件】医師意見書の受給者番号対応
        else if ("INSURER_NO_LABEL".equals(code)) obj = "保険者番号 見出し";
        else if ("INSURERD_NO_LABEL".equals(code)) obj = "被保険者番号 見出し";
        else if ("FD_OUTPUT_TIME_LABEL".equals(code)) obj = "作成日時 見出し";
        else if ("Label113".equals(code)) obj = "在宅・施設区分";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add end 【2009年度対応：追加案件】医師意見書の受給者番号対応
        else if ("INSURED_NO".equals(code)) obj = "被保険者番号";
        else if ("INSURER_NO".equals(code)) obj = "保険者番号";
        else if ("Grid6.h2.w1".equals(code)) obj = "意見書作成回数　「(２) 意見書作成回数」";
        else if ("Grid4.h3.w5".equals(code)) obj = "医療機関情報・医療機関所在地";
        else if ("Grid4.h3.w1".equals(code)) obj = "医療機関情報・医療機関所在地　「医療機関所在地」";
        else if ("Grid4.h2.w4".equals(code)) obj = "医療機関情報・医療機関名";
        else if ("Grid4.h2.w1".equals(code)) obj = "医療機関情報・医療機関名　「医療機関名」";
        else if ("Grid4.h1.w4".equals(code)) obj = "医療機関情報・医師氏名";
        else if ("Grid7.h2.w2".equals(code)) obj = "症状としての安定性　「(２) 症状としての安定性」";
        else if ("Grid13.h2.w3".equals(code)) obj = "症状名";
        else if ("Label8".equals(code)) obj = "心身の状態に関する意見　「３. 心身の状態に関する意見」";
        else if ("Label27".equals(code)) obj = "申請者・性別　「女」";
        else if ("Label25".equals(code)) obj = "申請者・生年月日・年号　「昭」";
        else if ("Label24".equals(code)) obj = "申請者・生年月日・年号　「大」";
        else if ("Grid1".equals(code)) obj = "申請者情報";
        else if ("Grid1.h2.w1".equals(code)) obj = "申請者情報　「申請者」";
        else if ("Grid1.h2.w3".equals(code)) obj = "申請者情報・氏名";
        else if ("Grid1.h2.w12".equals(code)) obj = "申請者情報・住所";
        else if ("Grid1.h3.w3".equals(code)) obj = "申請者情報・生年月日";
        else if ("Grid1.h1.w3".equals(code)) obj = "申請者情報・ふりがな";
        else if ("Grid1.h1.w2".equals(code)) obj = "申請者情報・ふりがな　「(ふりがな)」";
        else if ("Grid1.h1.w11".equals(code)) obj = "申請者情報・郵便番号1";
        else if ("Grid1.h1.w12".equals(code)) obj = "申請者情報・郵便番号　記号";
        else if ("Grid13.h2.w4".equals(code)) obj = "専門医受診の有無　「専門医受診の有無」";
        else if ("Grid10.h4.w1".equals(code)) obj = "特別な医療・失禁への対応　「失禁への対応」";
        else if ("Grid10.h3.w1".equals(code)) obj = "特別な医療・特別な対応　「特別な対応」";
        else if ("Label10".equals(code)) obj = "特別な医療2　「(過去１４日間以内に受けた医療のすべてにチェック)」";
        else if ("Grid11.h1.w1".equals(code)) obj = "日常生活の自立度等について　「(１) 日常生活の自立度等について」";
        else if ("Grid11.h2.w1".equals(code)) obj = "日常生活の自立度等について　「・障害高齢者の日常生活自立度（寝たきり度）」";
        else if ("Grid11.h3.w1".equals(code)) obj = "日常生活の自立度等について　「・認知症高齢者の日常生活自立度」";
        else if ("Label1".equals(code)) obj = "認知症の周辺症状　「(３) 認知症の周辺症状 （該当する項目全てチェック：」";
        else if ("Label2".equals(code)) obj = "認知症の周辺症状　「認知症以外の疾患で同様の症状を認める場合を含む」";
        else if ("Label5".equals(code)) obj = "認知症の中核症状　「(２) 認知症の中核症状」";
        else if ("Label17".equals(code)) obj = "認知症の中核症状　「（認知症以外の疾患で同様の症状を認める場合を含む）」";
        else if ("Grid12.h4.w1".equals(code)) obj = "認知症の中核症状　「・自分の意思の伝達能力」";
        else if ("Grid12.h2.w1".equals(code)) obj = "認知症の中核症状　「・短期記憶」";
        else if ("Grid12.h3.w1".equals(code)) obj = "認知症の中核症状　「・日常の意思決定を行うための認知能力」";
        else if ("Grid8.h1.w15".equals(code)) obj = "発症年月日1";
        else if ("Grid8.h2.w15".equals(code)) obj = "発症年月日2";
        else if ("Grid8.h3.w15".equals(code)) obj = "発症年月日3";
        else if ("Grid6.h1.w12".equals(code)) obj = "最終診察日";
        else if ("CORNER_BLOCK".equals(code)) obj = "「■」";
        else if ("FD_OUTPUT_TIME".equals(code)) obj = "タイムスタンプ";
        else if ("Grid11".equals(code)) obj = "日常生活の自立度等情報";
        else if ("Grid14".equals(code)) obj = "認知症の周辺症状情報";
        else if ("Grid7".equals(code)) obj = "傷病に関する意見情報";
        else if ("Grid13".equals(code)) obj = "その他の精神・神経症状情報";
        else if ("Grid3".equals(code)) obj = "同意情報";
        else if ("Grid4".equals(code)) obj = "医療機関情報";
        else if ("Grid10".equals(code)) obj = "特別な医療情報";
        else if ("Grid6".equals(code)) obj = "最終診察日情報等";
        else if ("Grid12".equals(code)) obj = "認知症の中核症状情報";
        else if ("Grid1.h1.w17".equals(code)) obj = "申請者情報・郵便番号2";
        else if ("Grid1.h3.w17".equals(code)) obj = "申請者情報・電話番号1";
        else if ("Grid1.h3.w19".equals(code)) obj = "申請者情報・電話番号2";
        else if ("Grid1.h3.w21".equals(code)) obj = "申請者情報・電話番号3";
        else if ("Grid2".equals(code)) obj = "記入日情報";
        else if ("Grid5".equals(code)) obj = "医療機関連絡先情報";
        else if ("Grid8".equals(code)) obj = "診断名情報";
        return obj;
    }
    /**
     * 意見書2ページ目の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatIkensho2(String code, Object obj){
        if ("Label75".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢徘徊｣　見出し";
        else if ("Label74".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢意欲低下｣　見出し";
        else if ("Label80".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢その他（｣　見出し";
        else if ("Label73".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢閉じこもり｣　見出し";
        else if ("Label79".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢がん等による疼痛｣　見出し";
        else if ("Label72".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢心肺機能の低下｣　見出し";
        else if ("Label78".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢易感染性｣　見出し";
        else if ("Label71".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢褥瘡｣　見出し";
        else if ("Label77".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢脱水｣　見出し";
        else if ("Label70".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢移動能力の低下｣　見出し";
        else if ("Label76".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢摂食・嚥下機能低下｣　見出し";
        else if ("Label69".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢転倒・骨折｣　見出し";
        else if ("Label68".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢低栄養｣　見出し";
        else if ("Label67".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針・｢尿失禁｣　見出し｣";
        else if ("Label4".equals(code)) obj = "（５）身体の状態・｢身長＝｣　見出し";
        else if ("INSURER_NO".equals(code)) obj = "保険者番号";
        else if ("INSURED_NO".equals(code)) obj = "被保険者番号";
        else if ("FD_OUTPUT_TIME".equals(code)) obj = "タイムスタンプ";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add begin 【2009年度対応：追加案件】医師意見書の受給者番号対応
        else if ("INSURER_NO_LABEL".equals(code)) obj = "保険者番号 見出し";
        else if ("INSURERD_NO_LABEL".equals(code)) obj = "被保険者番号 見出し";
        else if ("FD_OUTPUT_TIME_LABEL".equals(code)) obj = "作成日時 見出し";
        else if ("Label113".equals(code)) obj = "在宅・施設区分";
        else if ("Grid14.h1.w2".equals(code)) obj = "５．特記すべき事項・前回の要介護度における主治医意見書作成時点と比較して『介護の必要度』　見出し";
        else if ("Grid14.h1.w12".equals(code)) obj = "５．特記すべき事項・前回の要介護度　□減少　見出し";
        else if ("Grid14.h1.w11".equals(code)) obj = "５．特記すべき事項・前回の要介護度　□変化なし　見出し";
        else if ("Grid14.h1.w10".equals(code)) obj = "５．特記すべき事項・前回の要介護度　□増加　見出し";
        else if ("Label96".equals(code)) obj = "５．特記すべき事項・前回の要介護度・□減少　／";
        else if ("Label97".equals(code)) obj = "５．特記すべき事項・前回の要介護度・□変化なし　／";
        else if ("Label98".equals(code)) obj = "５．特記すべき事項・前回の要介護度・□増加　／";
        else if ("Grid27.h1.w1".equals(code)) obj = "５．特記すべき事項・｢長谷川式 =｣　見出し";
        else if ("Grid27.h1.w4".equals(code)) obj = "５．特記すべき事項・｢点 (｣　見出し";
        else if ("Grid27.h1.w9".equals(code)) obj = "５．特記すべき事項・｢年｣　見出し";
        else if ("Grid27.h1.w12".equals(code)) obj = "５．特記すべき事項・｢月　)｣　見出し";
        else if ("Grid27.h1.w14".equals(code)) obj = "５．特記すべき事項・｢(前回｣　見出し";
        else if ("Grid27.h1.w19".equals(code)) obj = "５．特記すべき事項・前回・｢点 (｣　見出し";
        else if ("Grid27.h1.w24".equals(code)) obj = "５．特記すべき事項・前回・｢年｣　見出し";
        else if ("Grid27.h1.w27".equals(code)) obj = "５．特記すべき事項・前回・｢月 ))｣　見出し";
        else if ("Grid28.h1.w1".equals(code)) obj = "５．特記すべき事項・｢施設選択(優先度)｣・上段・１　見出し";
        else if ("Grid28.h1.w3".equals(code)) obj = "５．特記すべき事項・施設選択（優先度）・上段・１　見出し";
        else if ("Grid28.h1.w6".equals(code)) obj = "５．特記すべき事項・施設選択（優先度）・上段・１　内容";
        else if ("Grid28.h1.w7".equals(code)) obj = "５．特記すべき事項・施設選択（優先度）・上段・２　見出し";
        else if ("Grid28.h1.w10".equals(code)) obj = "５．特記すべき事項・｢施設選択（優先度）｣・上段・２　内容";
        else if ("Grid26.h1.w2".equals(code)) obj = "要介護認定結果の情報提供を希望　見出し";
        else if ("Grid26.h1.w10".equals(code)) obj = "要介護認定結果の情報提供を希望・□する　□しない　見出し";
        else if ("Label94".equals(code)) obj = "要介護認定結果の情報提供を希望・□する　／";
        else if ("Label95".equals(code)) obj = "要介護認定結果の情報提供を希望・□しない　／";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add end 【2009年度対応：追加案件】医師意見書の受給者番号対応
        else if ("Label6".equals(code)) obj = "ページ数";
        else if ("Label7".equals(code)) obj = "「４. 生活機能とサービスに関する意見」　見出し";
        else if ("Label9".equals(code)) obj = "特記すべき事項・「５. 特記すべき事項」　見出し";
        else if ("Label10".equals(code)) obj = "特記すべき事項・｢要介護認定及び介護サービス計画作成時に必要な医学的なご意見等を記載して下さい。なお、専門医等に別途意見を求め｣　見出し";
        else if ("Label12".equals(code)) obj = "（５）医学的管理の必要性・｢(特に必要性の高いものには下線を引いて下さい。予防給付により提供されるサービスを含みます。)｣　見出し";
        else if ("Label8".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針　その他";
        else if ("Grid8".equals(code)) obj = "（３）現在あるかまたは今後欲せ医の可能性の高い状態とその対処方針　枠";
        else if ("Grid8.h1.w1".equals(code)) obj = "｢(３)現在あるかまたは今後発生の可能性の高い状態とその対処方針｣　見出し";
        else if ("Grid3".equals(code)) obj = "（５）身体の状態　枠";
        else if ("Grid3.h1.w1".equals(code)) obj = "｢(５)身体の状態｣　見出し";
        else if ("Grid1.h3.w18".equals(code)) obj = "（５）身体の状態　四肢欠損";
        else if ("Grid1.h7.w18".equals(code)) obj = "（５）身体の状態　筋力の低下";
        else if ("Grid1.h6.w18".equals(code)) obj = "（５）身体の状態　関節の拘縮";
        else if ("Grid1.h5.w18".equals(code)) obj = "（５）身体の状態　関節の痛み";
        else if ("Grid1.h1.w18".equals(code)) obj = "（５）身体の状態　褥瘡";
        else if ("Grid1.h2.w18".equals(code)) obj = "（５）身体の状態　その他の皮膚疾患";
        else if ("Grid23.h1.w1".equals(code)) obj = "｢(１)移動｣　見出し";
        else if ("Grid24.h1.w1".equals(code)) obj = "（１）移動・｢屋外歩行｣　見出し";
        else if ("Grid24.h2.w1".equals(code)) obj = "（１）移動・｢車いすの使用｣　見出し";
        else if ("Grid24.h3.w1".equals(code)) obj = "（１）移動・｢歩行補助具･装具の使用｣　見出し";
        else if ("Grid21.h1.w1".equals(code)) obj = "(２)栄養・食生活・｢(２)栄養・食生活｣　見出し";
        else if ("Grid22.h1.w1".equals(code)) obj = "(２)栄養・食生活・｢食事行為｣　見出し";
        else if ("Grid22.h2.w1".equals(code)) obj = "(２)栄養・食生活・｢現在の栄養状態｣　見出し";
        else if ("Grid4.h1.w1".equals(code)) obj = "（５）身体の状態・｢利き腕（」　見出し";
        else if ("Grid4.h1.w2".equals(code)) obj = "（５）身体の状態　身長";
        else if ("Grid4.h1.w3".equals(code)) obj = "（５）身体の状態・｢cm 体重＝｣　見出し";
        else if ("Grid4.h1.w4".equals(code)) obj = "（５）身体の状態　体重";
        else if ("Grid4.h1.w9".equals(code)) obj = "（５）身体の状態・｢cm 体重＝kg｣　見出し";
        else if ("Grid13.h1.w1".equals(code)) obj = "｢(６)サービス提供時における医学的観点からの留意事項｣　見出し";
        else if ("Grid5.h1.w1".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項･｢血圧｣　見出し";
        else if ("Grid5.h1.w2".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項　血圧";
        else if ("Grid5.h1.w3".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項・｢移動｣)見出し";
        else if ("Grid5.h1.w5".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項　移動";
        else if ("Grid5.h1.w6".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項・移動・｢)｣　見出し";
        else if ("Grid5.h2.w1".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項・｢･摂食」　見出し";
        else if ("Grid5.h2.w2".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項　摂食";
        else if ("Grid5.h2.w3".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項・｢)･運動」　見出し";
        else if ("Grid5.h2.w5".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項　運動";
        else if ("Grid5.h2.w6".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項・運動・｢)」　見出し";
        else if ("Grid5.h3.w1".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項・｢･嚥下｣　見出し";
        else if ("Grid5.h3.w2".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項　嚥下";
        else if ("Grid5.h3.w3".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項・｢)･その他 (｣　見出し";
        else if ("Grid5.h3.w4".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項　その他";
        else if ("Grid5.h3.w6".equals(code)) obj = "(６)サービス提供時における医学的観点からの留意事項・その他｢)｣　見出し";
        else if ("Grid15.h1.w1".equals(code)) obj = "(７)感染症の有無・｢(７)感染症の有無 (有の場合は具体的に記入して下さい)｣　見出し";
        else if ("Grid16.h1.w5".equals(code)) obj = "（７）感染症の有無・有　内容";
        else if ("Grid16.h1.w3".equals(code)) obj = "(７)感染症の有無・｢)｣　見出し";
        else if ("Grid11.h1.w1".equals(code)) obj = "(５)医学的管理の必要性　見出し";
        else if ("Grid70.h1.w1".equals(code)) obj = "(５)医学的管理の必要性・｢□訪問診療｣　見出し";
        else if ("Grid70.h1.w2".equals(code)) obj = "(５)医学的管理の必要性・｢□訪問看護｣　見出し";
        else if ("Grid70.h1.w3".equals(code)) obj = "(５)医学的管理の必要性・｢□訪問歯科診療｣　見出し";
        else if ("Grid70.h1.w4".equals(code)) obj = "(５)医学的管理の必要性・「□訪問薬剤管理指導」　見出し";
        else if ("Grid70.h2.w1".equals(code)) obj = "(５)医学的管理の必要性・「□訪問リハビリテーション」　見出し";
        else if ("Grid70.h2.w2".equals(code)) obj = "(５)医学的管理の必要性・「□短期入所療養介護」　見出し";
        else if ("Grid70.h2.w3".equals(code)) obj = "(５)医学的管理の必要性・「□訪問歯科衛生指導」　見出し";
        else if ("Grid2.h1.w4".equals(code)) obj = "利用者情報・｢歳 (｣　見出し";
        else if ("Grid2.h1.w7".equals(code)) obj = "利用者情報・｢年｣　見出し";
        else if ("Grid2.h1.w9".equals(code)) obj = "利用者情報・｢月｣　見出し";
        else if ("Grid2.h1.w11".equals(code)) obj = "利用者情報・｢日)｣　見出し";
        else if ("Grid6.h1.w1".equals(code)) obj = "｢(４)サービス利用による生活機能の維持･改善の見通し｣　見出し";
        else if ("Grid12.h1.w1".equals(code)) obj = "(５)医学的管理の必要性・｢□通所リハビリテーション｣　見出し";
        else if ("Grid12.h1.w2".equals(code)) obj = "(５)医学的管理の必要性・｢□その他の医療系サービス　(｣　見出し";
        else if ("Grid12.h1.w3".equals(code)) obj = "(５)医学的管理の必要性　その他の医療系サービス";
        else if ("Grid12.h1.w4".equals(code)) obj = "(５)医学的管理の必要性・｢)｣　見出し";
        else if ("Grid7.h1.w1".equals(code)) obj = "(４)サービス利用による生活機能の維持･改善の見通し・｢□期待できる｣　見出し";
        else if ("Grid7.h1.w2".equals(code)) obj = "(４)サービス利用による生活機能の維持･改善の見通し・｢□期待できない｣　見出し";
        else if ("Grid7.h1.w3".equals(code)) obj = "(４)サービス利用による生活機能の維持･改善の見通し・｢□不明｣　見出し";
        else if ("Grid10.h1.w1".equals(code)) obj = "(３)現在あるかまたは今後発生の可能性の高い状態とその対処方針・｢対処方針｣　見出し";
        else if ("Grid10.h1.w2".equals(code)) obj = "(３)現在あるかまたは今後発生の可能性の高い状態とその対処方針　対処方針";
        else if ("Grid10.h1.w3".equals(code)) obj = "(３)現在あるかまたは今後発生の可能性の高い状態とその対処方針・対処方針・｢)｣　見出し";
        else if ("Grid25.h1.w1".equals(code)) obj = "（２）栄養・食生活・｢栄養・食生活上の留意点｣　見出し";
        else if ("Grid25.h1.w2".equals(code)) obj = "（２）栄養・食生活　栄養・食生活上の留意点";
        else if ("Grid25.h1.w3".equals(code)) obj = "（２）栄養・食生活・栄養・食生活上の留意点・｢)｣　見出し";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 replace begin 【2009年度対応：追加案件】医師意見書の受給者番号対応
        else if ("Grid18.h1.w1".equals(code)) obj = "５．特記すべき事項・｢長谷川式 =｣・中段　見出し";
        else if ("Grid18.h1.w4".equals(code)) obj = "５．特記すべき事項・｢点 (｣・中段　見出し";
        else if ("Grid18.h1.w9".equals(code)) obj = "５．特記すべき事項・｢年｣・中段　見出し";
        else if ("Grid18.h1.w12".equals(code)) obj = "５．特記すべき事項・｢月　)｣・中段　見出し";
        else if ("Grid18.h1.w14".equals(code)) obj = "５．特記すべき事項・｢(前回｣・中段　見出し";
        else if ("Grid18.h1.w19".equals(code)) obj = "５．特記すべき事項・前回・｢点 (｣・中段　見出し";
        else if ("Grid18.h1.w24".equals(code)) obj = "５．特記すべき事項・前回・｢年｣・中段　見出し";
        else if ("Grid18.h1.w27".equals(code)) obj = "５．特記すべき事項・前回・｢月 ))｣・中段　見出し";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 replace end 【2009年度対応：追加案件】医師意見書の受給者番号対応
        else if ("Grid19.h1.w1".equals(code)) obj = "５．特記すべき事項・｢施設選択(優先度)｣・下段・１　見出し";
        else if ("Grid19.h1.w3".equals(code)) obj = "５．特記すべき事項・施設選択（優先度）・下段・１　見出し";
        else if ("Grid19.h1.w6".equals(code)) obj = "５．特記すべき事項・施設選択（優先度）・下段・１　内容";
        else if ("Grid19.h1.w7".equals(code)) obj = "５．特記すべき事項・施設選択（優先度）・下段・２　見出し";
        else if ("Grid19.h1.w10".equals(code)) obj = "５．特記すべき事項・施設選択（優先度）・下段・２　内容";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 replace begin 【2009年度対応：追加案件】医師意見書の受給者番号対応
        else if ("Grid20.h1.w1".equals(code)) obj = "５．特記すべき事項・｢施設選択(優先度)｣・中段・１　見出し";
        else if ("Grid20.h1.w3".equals(code)) obj = "５．特記すべき事項・施設選択（優先度）・中段・１　見出し";
        else if ("Grid20.h1.w6".equals(code)) obj = "５．特記すべき事項・施設選択（優先度）・中段・１　内容";
        else if ("Grid20.h1.w7".equals(code)) obj = "５．特記すべき事項・施設選択（優先度）・中段・２　見出し";
        else if ("Grid20.h1.w10".equals(code)) obj = "５．特記すべき事項・｢施設選択（優先度）｣・中段・２　内容";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 replace end 【2009年度対応：追加案件】医師意見書の受給者番号対応
        return obj;
    }

    /**
     * 訪問看護指示書（医療機関）の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatShijisho(String code, Object obj){
        if ("Label22".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「４．その他」";
        else if ("Label21".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「３. 装着・使用医療機器等の操作援助・管理」";
        else if ("Label20".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「２. 褥瘡の処置等」";
        else if ("Label19".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「１. リハビリテーション」";
        else if ("Label18".equals(code)) obj = "要介護認定の状況　「５ ）」";
        else if ("Label17".equals(code)) obj = "要介護認定の状況　「４」";
        else if ("Label16".equals(code)) obj = "要介護認定の状況　「３」";
        else if ("Label15".equals(code)) obj = "要介護認定の状況　「２」";
        else if ("Label14".equals(code)) obj = "要介護認定の状況　「１」";
        else if ("Label10".equals(code)) obj = "生年月日・元号　「平」　見出し";
        else if ("Label8".equals(code)) obj = "生年月日・元号　「大」　見出し";
        else if ("Label7".equals(code)) obj = "生年月日・元号　「明｣　見出し";
        else if ("Shape27".equals(code)) obj = "日常生活自立度・寝たきり度　「自立　／」";
        else if ("Label9".equals(code)) obj = "生年月日・元号　「昭」　見出し";
        else if ("title".equals(code)) obj = "「訪問看護指示書」　見出し";
        else if ("Label1".equals(code)) obj = "「上記のとおり、指定訪問看護の実施を指示いたします。」　見出し";
        else if ("Label2".equals(code)) obj = "作成年月日　「平成  年  月  日」";
        else if ("Label3".equals(code)) obj = "訪問看護ステーション　「印」　見出し";
        else if ("Label4".equals(code)) obj = "「医療機関名」　見出し";
        else if ("Label5".equals(code)) obj = "「医療機関医師氏名」　見出し";
        else if ("Label6".equals(code)) obj = "訪問看護ステーション名";
        else if ("Label23".equals(code)) obj = "「要介護認定の状況」　見出し";
        else if ("Label24".equals(code)) obj = "「在宅患者訪問点滴注射に関する指示（投与薬剤・投与量・投与方法等）」　見出し";
        else if ("Grid13.h1.w2".equals(code)) obj = "医療機関名";
        else if ("Grid13.h2.w1".equals(code)) obj = "「住　　　所」　見出し";
        else if ("Grid13.h2.w2".equals(code)) obj = "医療機関住所";
        else if ("Grid13.h3.w1".equals(code)) obj = "「電　　　話」　見出し";
        else if ("Grid13.h3.w2".equals(code)) obj = "医療機関　電話番号";
        else if ("Grid13.h4.w1".equals(code)) obj = "「（ＦＡＸ）」　見出し";
        else if ("Grid13.h4.w2".equals(code)) obj = "医療機関　ＦＡＸ番号";
        else if ("Grid13.h5.w2".equals(code)) obj = "医療機関医師氏名";
        else if ("Grid8".equals(code)) obj = "日常生活　枠";
        else if ("Grid8.h1.w1".equals(code)) obj = "日常生活自立度　「日常生活」　見出し";
        else if ("Grid8.h1.w2".equals(code)) obj = "「寝たきり度」　見出し";
        else if ("Grid8.h1.w19".equals(code)) obj = " J1";
        else if ("Grid8.h1.w17".equals(code)) obj = " J2";
        else if ("Grid8.h1.w15".equals(code)) obj = " A1";
        else if ("Grid8.h1.w13".equals(code)) obj = " A2";
        else if ("Grid8.h1.w11".equals(code)) obj = " B1";
        else if ("Grid8.h1.w9".equals(code)) obj = " B2";
        else if ("Grid8.h1.w7".equals(code)) obj = " C1";
        else if ("Grid8.h1.w5".equals(code)) obj = " C2";
        else if ("Grid8.h2.w1".equals(code)) obj = "日常生活自立度　「自 立 度」　見出し";
        else if ("Grid8.h2.w2".equals(code)) obj = "「認知症の状況」　見出し";
        else if ("Grid8.h2.w19".equals(code)) obj = " I";
        else if ("Grid8.h2.w17".equals(code)) obj = " IIａ";
        else if ("Grid8.h2.w15".equals(code)) obj = " IIｂ";
        else if ("Grid8.h2.w13".equals(code)) obj = " IIIａ";
        else if ("Grid8.h2.w11".equals(code)) obj = " IIIｂ";
        else if ("Grid8.h2.w9".equals(code)) obj = " IV";
        else if ("Grid8.h2.w7".equals(code)) obj = " Ｍ";
        else if ("Grid8.h3.w21".equals(code)) obj = "要支援";
        else if ("Grid8.h3.w19".equals(code)) obj = "要介護";
        else if ("Grid10".equals(code)) obj = "留意事項及び指示事項　枠";
        else if ("Grid10.h1.w1".equals(code)) obj = "「留意事項及び指示事項」　見出し";
        else if ("Grid10.h13.w1".equals(code)) obj = "「 I 療養生活指導上の留意事項」　見出し";
        else if ("Grid10.h2.w2".equals(code)) obj = " I 療養生活指導上の留意事項";
        else if ("Grid10.h3.w1".equals(code)) obj = "「II」 　見出し";
        else if ("Grid10.h4.w2".equals(code)) obj = "１. リハビリテーション";
        else if ("Grid10.h6.w2".equals(code)) obj = "２. 褥瘡の処置等";
        else if ("Grid10.h8.w2".equals(code)) obj = "３. 装着・使用医療機器等の操作援助・管理";
        else if ("Grid10.h12.w2".equals(code)) obj = "４. その他";
        else if ("Grid10.h10.w2".equals(code)) obj = "在宅患者訪問点滴注射に関する指示（投与薬剤・投与量・投与方法等）";
        else if ("Grid1".equals(code)) obj = "訪問看護指示期間・点滴注射指示期間　枠";
        else if ("Grid1.h1.w14".equals(code)) obj = "「訪問看護指示期間」　見出し";
        else if ("Grid1.h1.w2".equals(code)) obj = "訪問看護指示期間　開始年";
        else if ("Grid1.h1.w3".equals(code)) obj = "訪問看護指示期間　開始年　見出し";
        else if ("Grid1.h1.w4".equals(code)) obj = "訪問看護指示期間　開始月";
        else if ("Grid1.h1.w5".equals(code)) obj = "訪問看護指示期間　開始月　見出し";
        else if ("Grid1.h1.w6".equals(code)) obj = "訪問看護指示期間　開始日";
        else if ("Grid1.h1.w7".equals(code)) obj = "訪問看護指示期間　開始日　見出し";
        else if ("Grid1.h1.w8".equals(code)) obj = "訪問看護指示期間　終了年";
        else if ("Grid1.h1.w9".equals(code)) obj = "訪問看護指示期間　終了年　見出し";
        else if ("Grid1.h1.w10".equals(code)) obj = "訪問看護指示期間　終了月";
        else if ("Grid1.h1.w11".equals(code)) obj = "訪問看護指示期間　終了月　見出し";
        else if ("Grid1.h1.w12".equals(code)) obj = "訪問看護指示期間　終了日";
        else if ("Grid1.h1.w13".equals(code)) obj = "訪問看護指示期間　終了日　見出し";
        else if ("Grid1.h2.w14".equals(code)) obj = "「点滴注射指示期間」　見出し";
        else if ("Grid1.h2.w2".equals(code)) obj = "点滴注射指示期間　開始年";
        else if ("Grid1.h2.w3".equals(code)) obj = "点滴注射指示期間　開始年　見出し";
        else if ("Grid1.h2.w4".equals(code)) obj = "点滴注射指示期間　開始月";
        else if ("Grid1.h2.w5".equals(code)) obj = "点滴注射指示期間　開始月　見出し";
        else if ("Grid1.h2.w6".equals(code)) obj = "点滴注射指示期間　開始日";
        else if ("Grid1.h2.w7".equals(code)) obj = "点滴注射指示期間　開始日　見出し";
        else if ("Grid1.h2.w8".equals(code)) obj = "点滴注射指示期間　終了年";
        else if ("Grid1.h2.w9".equals(code)) obj = "点滴注射指示期間　終了年　見出し";
        else if ("Grid1.h2.w10".equals(code)) obj = "点滴注射指示期間　終了月";
        else if ("Grid1.h2.w11".equals(code)) obj = "点滴注射指示期間　終了月　見出し";
        else if ("Grid1.h2.w12".equals(code)) obj = "点滴注射指示期間　終了日";
        else if ("Grid1.h2.w13".equals(code)) obj = "点滴注射指示期間　終了日　見出し";
        else if ("Grid2.h1.w1".equals(code)) obj = "「患者氏名」　見出し";
        else if ("Grid2.h1.w2".equals(code)) obj = "患者氏名";
        else if ("Grid2.h1.w3".equals(code)) obj = "「生年月日」";
        else if ("Grid2.h1.w5".equals(code)) obj = "生年月日・年";
        else if ("Grid2.h1.w6".equals(code)) obj = "生年月日・年　見出し";
        else if ("Grid2.h1.w7".equals(code)) obj = "生年月日・月";
        else if ("Grid2.h1.w8".equals(code)) obj = "生年月日・月　見出し";
        else if ("Grid2.h1.w9".equals(code)) obj = "生年月日・日";
        else if ("Grid2.h1.w10".equals(code)) obj = "生年月日・日　見出し";
        else if ("Grid2.h1.w12".equals(code)) obj = "歳）";
        else if ("Grid3.h1.w1".equals(code)) obj = "「患者住所」　見出し";
        else if ("Grid3.h1.w3".equals(code)) obj = "「〒」　見出し";
        else if ("Grid3.h1.w4".equals(code)) obj = "患者郵便番号";
        else if ("Grid3.h2.w4".equals(code)) obj = "患者住所";
        else if ("Grid3.h3.w2".equals(code)) obj = "電話番号";
        else if ("Grid3.h3.w6".equals(code)) obj = "「電話」　見出し";
        else if ("Grid4.h1.w1".equals(code)) obj = "「主たる傷病名」　見出し";
        else if ("Grid4.h1.w2".equals(code)) obj = "主たる傷病名";
        else if ("Grid5.h1.w1".equals(code)) obj = "「現在の状況」　見出し";
        else if ("Grid6.h1.w1".equals(code)) obj = "「症状・治療状態」　見出し";
        else if ("Grid6.h1.w2".equals(code)) obj = "症状・治療状態";
        else if ("Grid7.h1.w1".equals(code)) obj = "投与中の薬剤の用法・用量・「投与中の」　見出し";
        else if ("Grid7.h1.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「１」　見出し";
        else if ("Grid7.h1.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「１」";
        else if ("Grid7.h1.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「２」　見出し";
        else if ("Grid7.h1.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「２」";
        else if ("Grid7.h2.w1".equals(code)) obj = "投与中の薬剤の用法・用量・「薬剤の用」";
        else if ("Grid7.h2.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「３」　見出し";
        else if ("Grid7.h2.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「３」";
        else if ("Grid7.h2.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「４」　見出し";
        else if ("Grid7.h2.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「４」";
        else if ("Grid7.h3.w1".equals(code)) obj = "投与中の薬剤の用法・用量・「法・用量」";
        else if ("Grid7.h3.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「５」　見出し";
        else if ("Grid7.h3.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「５」";
        else if ("Grid7.h3.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「６」　見出し";
        else if ("Grid7.h3.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「６」";
        else if ("Grid9".equals(code)) obj = "「装着・使用医療機器等」　枠";
        else if ("Grid9.h1.w3".equals(code)) obj = "１．自動腹膜灌流装置・「1」　見出し";
        else if ("Grid9.h1.w4".equals(code)) obj = "１．自動腹膜灌流装置・「自動腹膜灌流装置」　見出し";
        else if ("Grid9.h1.w7".equals(code)) obj = "２．透析液供給装置・「2」　見出し";
        else if ("Grid9.h1.w8".equals(code)) obj = "２．透析液供給装置・「透析液供給装置」　見出し";
        else if ("Grid9.h1.w15".equals(code)) obj = "３．酸素療法・「3」　見出し";
        else if ("Grid9.h1.w24".equals(code)) obj = "３．酸素療法・「酸素療法（」　見出し";
        else if ("Grid9.h1.w20".equals(code)) obj = "３．酸素療法";
        else if ("Grid9.h1.w21".equals(code)) obj = "３．酸素療法・「 l/min」　見出し";
        else if ("Grid9.h1.w22".equals(code)) obj = "３．酸素療法・「）」　見出し";
        else if ("Grid9.h2.w1".equals(code)) obj = "装着・使用医療機器等・「装着・使」　見出し";
        else if ("Grid9.h2.w3".equals(code)) obj = "４．吸引器・「4」　見出し";
        else if ("Grid9.h2.w4".equals(code)) obj = "４．吸引器・「吸引器」　見出し";
        else if ("Grid9.h2.w7".equals(code)) obj = "５．中心静脈栄養・「5」　見出し";
        else if ("Grid9.h2.w8".equals(code)) obj = "５．中心静脈栄養・「中心静脈栄養」　見出し";
        else if ("Grid9.h2.w15".equals(code)) obj = "６．輸液ポンプ・「6」　見出し";
        else if ("Grid9.h2.w24".equals(code)) obj = "６．輸液ポンプ・「輸液ポンプ」　見出し";
        else if ("Grid9.h3.w1".equals(code)) obj = "装着・使用医療機器等・「用医療機」　見出し";
        else if ("Grid9.h3.w3".equals(code)) obj = "７．経管栄養・「7」　見出し";
        else if ("Grid9.h3.w4".equals(code)) obj = "７．経管栄養・「経管栄養　　　（」　見出し";
        else if ("Grid9.h3.w23".equals(code)) obj = "７．経管栄養";
        else if ("Grid9.h3.w9".equals(code)) obj = "７．経管栄養・「：チューブサイズ」　見出し";
        else if ("Grid9.h3.w15".equals(code)) obj = "７．経管栄養・「：チューブサイズ」";
        else if ("Grid9.h3.w16".equals(code)) obj = "７．経管栄養・「、」　見出し";
        else if ("Grid9.h3.w18".equals(code)) obj = "７．経管栄養　日";
        else if ("Grid9.h3.w19".equals(code)) obj = "７．経管栄養・「日に1回交換」　見出し";
        else if ("Grid9.h3.w22".equals(code)) obj = "７．経管栄養・「）」　見出し";
        else if ("Grid9.h4.w1".equals(code)) obj = "装着・使用医療機器等・「 器等」　見出し";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete begin 【2012年度対応】訪問指示書の留置カテーテル部位追加
//        else if ("Grid9.h4.w3".equals(code)) obj = "８．留置カテーテル・「8」　見出し";
//        else if ("Grid9.h4.w4".equals(code)) obj = "８．留置カテーテル・「留置カテーテル（サイズ」　見出し";
//        else if ("Grid9.h4.w9".equals(code)) obj = "８．留置カテーテル　サイズ";
//        else if ("Grid9.h4.w13".equals(code)) obj = "８．留置カテーテル・「、」　見出し";
//        else if ("Grid9.h4.w18".equals(code)) obj = "８．留置カテーテル　日";
//        else if ("Grid9.h4.w19".equals(code)) obj = "８．留置カテーテル・「 日に1回交換」　見出し";
//        else if ("Grid9.h4.w22".equals(code)) obj = "８．留置カテーテル・「）」　見出し";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete end
        //[ID:0000689][Shin Fujihara] 2012/03/12 add begin 【2012年度対応】訪問指示書の留置カテーテル部位追加
        else if ("Grid9.h4.w3".equals(code)) obj = "８．留置カテーテル・「8」　見出し";
        else if ("Grid9.h4.w4".equals(code)) obj = "８．留置カテーテル・「留置カテーテル（部位：」　見出し";
        else if ("Grid9.h4.w23".equals(code)) obj = "８．留置カテーテル　部位";
        else if ("Grid9.h4.w12".equals(code)) obj = "８．留置カテーテル・「留置カテーテル（サイズ」　見出し";
        else if ("Grid9.h4.w15".equals(code)) obj = "８．留置カテーテル　サイズ";
        else if ("Grid9.h4.w17".equals(code)) obj = "８．留置カテーテル・「、」　見出し";
        else if ("Grid9.h4.w18".equals(code)) obj = "８．留置カテーテル　日";
        else if ("Grid9.h4.w19".equals(code)) obj = "８．留置カテーテル・「 日に1回交換」　見出し";
        else if ("Grid9.h4.w22".equals(code)) obj = "８．留置カテーテル・「）」　見出し";
        //[ID:0000689][Shin Fujihara] 2012/03/12 add end
        else if ("Grid9.h5.w1".equals(code)) obj = "装着・使用医療機器等・「 （番号に」　見出し";
        else if ("Grid9.h5.w3".equals(code)) obj = "９．人工呼吸器・「9」　見出し";
        else if ("Grid9.h5.w4".equals(code)) obj = "９．人工呼吸器・「人工呼吸器　　（」　見出し";
        else if ("Grid9.h5.w8".equals(code)) obj = "９．人工呼吸器　種類";
        else if ("Grid9.h5.w10".equals(code)) obj = "９．人工呼吸器・「：設定」　見出し";
        else if ("Grid9.h5.w6".equals(code)) obj = "９．人工呼吸器　設定";
        else if ("Grid9.h5.w22".equals(code)) obj = "９．人工呼吸器・「）」　見出し";
        else if ("Grid9.h6.w1".equals(code)) obj = "装着・使用医療機器等・「 ○印）」　見出し";
        else if ("Grid9.h6.w3".equals(code)) obj = "１０．気管カニューレ・「10｣　見出し";
        else if ("Grid9.h6.w4".equals(code)) obj = "１０．気管カニューレ・「気管カニューレ（サイズ」　見出し";
        else if ("Grid9.h6.w9".equals(code)) obj = "１０．気管カニューレ　サイズ";
        else if ("Grid9.h6.w11".equals(code)) obj = "１０．気管カニューレ・「）」　見出し";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete begin 【2012年度対応】訪問指示書のドレーン削除
//        else if ("Grid9.h6.w12".equals(code)) obj = "１１．ドレーン・「11」　見出し";
//        else if ("Grid9.h6.w13".equals(code)) obj = "１１．ドレーン・「ドレーン（部位：」　見出し";
//        else if ("Grid9.h6.w20".equals(code)) obj = "１１．ドレーン　部位";
//        else if ("Grid9.h6.w22".equals(code)) obj = "１１．ドレーン・「）」　見出し";
//        else if ("Grid9.h7.w3".equals(code)) obj = "１２．人工肛門・「12」　見出し";
//        else if ("Grid9.h7.w4".equals(code)) obj = "１２．人工肛門・「人工肛門」　見出し";
//        else if ("Grid9.h7.w5".equals(code)) obj = "１３．人工膀胱・「13」　見出し";
//        else if ("Grid9.h7.w23".equals(code)) obj = "１３．人工膀胱・「人工膀胱」　見出し";
//        else if ("Grid9.h7.w10".equals(code)) obj = "１４・その他・「14」　見出し";
//        else if ("Grid9.h7.w11".equals(code)) obj = "１４・その他・「その他（」　見出し";
//        else if ("Grid9.h7.w14".equals(code)) obj = "１４．その他";
//        else if ("Grid9.h7.w22".equals(code)) obj = "１４・その他・「）」　見出し";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete end
        //[ID:0000689][Shin Fujihara] 2012/03/12 add begin 【2012年度対応】訪問指示書のドレーン削除
        else if ("Grid9.h7.w3".equals(code)) obj = "１１．人工肛門・「11」　見出し";
        else if ("Grid9.h7.w4".equals(code)) obj = "１１．人工肛門・「人工肛門」　見出し";
        else if ("Grid9.h7.w5".equals(code)) obj = "１２．人工膀胱・「12」　見出し";
        else if ("Grid9.h7.w23".equals(code)) obj = "１２．人工膀胱・「人工膀胱」　見出し";
        else if ("Grid9.h7.w10".equals(code)) obj = "１３・その他・「13」　見出し";
        else if ("Grid9.h7.w11".equals(code)) obj = "１３・その他・「その他（」　見出し";
        else if ("Grid9.h7.w14".equals(code)) obj = "１３．その他";
        else if ("Grid9.h7.w22".equals(code)) obj = "１３・その他・「）」　見出し";
        //[ID:0000689][Shin Fujihara] 2012/03/12 add end
        else if ("Grid11.h1.w1".equals(code)) obj = "「緊急時の連絡先」　見出し";
        else if ("Grid11.h1.w2".equals(code)) obj = "緊急時の連絡先";
        else if ("Grid11.h2.w1".equals(code)) obj = "「不在時の対応法」　見出し";
        else if ("Grid11.h2.w2".equals(code)) obj = "不在時の対応法";
        else if ("Grid12.h1.w1".equals(code)) obj = "特記すべき留意事項・「特記すべき留意事項」　見出し";
        else if ("Grid12.h1.w6".equals(code)) obj = "特記すべき留意事項・「（注：薬の相互作用・副作用についての留意点、薬物アレルギーの既往等あれば記載して下さい。）」　見出し";
        else if ("Grid12.h2.w1".equals(code)) obj = "特記すべき留意事項";
        else if ("Grid12.h3.w1".equals(code)) obj = "他の訪問看護ステーションへの指示・「他の訪問看護ステーションへの指示」　見出し";
        else if ("Grid12.h4.w1".equals(code)) obj = "他の訪問看護ステーションへの指示・「（」　見出し ";
        else if ("Grid12.h4.w2".equals(code)) obj = "他の訪問看護ステーションへの指示・「無」　見出し";
        else if ("Grid12.h4.w4".equals(code)) obj = "他の訪問看護ステーションへの指示・「有」　見出し";
        else if ("Grid12.h4.w5".equals(code)) obj = "他の訪問看護ステーションへの指示・「：指定訪問看護ステーション名」　見出し";
        else if ("Grid12.h4.w6".equals(code)) obj = "他の訪問看護ステーション";
        else if ("Grid12.h4.w8".equals(code)) obj = "他の訪問看護ステーションへの指示・「 殿」　見出し";
        else if ("Grid12.h4.w7".equals(code)) obj = "他の訪問看護ステーションへの指示・「）」　見出し";
        
        //[ID:0000732][Shin Fujihara] 2012/04/20 add begin 【2012年度対応：訪問看護指示書】たん吸引指示追加
        else if ("Grid12.h6.w1".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「たんの吸引等実施のための訪問介護事業所への指示」　見出し";
        else if ("Grid12.h8.w1".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「（」　見出し ";
        else if ("Grid12.h8.w2".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「無」　見出し";
        else if ("Grid12.h8.w4".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「有」　見出し";
        else if ("Grid12.h8.w5".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「：訪問介護事業所名」　見出し";
        else if ("Grid12.h8.w6".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所";
        else if ("Grid12.h8.w8".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「 殿」　見出し";
        else if ("Grid12.h8.w7".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「）」　見出し";
        //[ID:0000732][Shin Fujihara] 2012/04/20 add end 【2012年度対応：訪問看護指示書】たん吸引指示追加
        
        //[ID:0000639][Shin Fujihara] 2011/03 add begin 「褥瘡の深さ」対応漏れ
        else if ("Label26".equals(code)) obj = "「褥瘡の深さ」　見出し";
        else if ("Grid15.h1.w4".equals(code)) obj = "褥瘡の深さ　「NPUAP分類」";
        else if ("Grid15.h1.w9".equals(code)) obj = "褥瘡の深さ・NPUAP分類　「Ⅲ度」";
        else if ("Grid15.h1.w7".equals(code)) obj = "褥瘡の深さ・NPUAP分類　「Ⅳ度」";
        else if ("Grid15.h1.w14".equals(code)) obj = "褥瘡の深さ　「DESIGN分類」";
        else if ("Grid15.h1.w18".equals(code)) obj = "褥瘡の深さ・DESIGN分類　「D3」";
        else if ("Grid15.h1.w16".equals(code)) obj = "褥瘡の深さ・DESIGN分類　「D4」";
        else if ("Grid15.h1.w19".equals(code)) obj = "褥瘡の深さ・DESIGN分類　「D5」";
        //[ID:0000639][Shin Fujihara] 2011/03 add end
        return obj;
    }

    /**
     * 訪問看護指示書（介護老人保健施設）の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatShijishoB(String code, Object obj){
        if ("Label22".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「４．その他」";
        else if ("Label21".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「３. 装着・使用医療機器等の操作援助・管理」";
        else if ("Label20".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「２. 褥瘡の処置等」";
        else if ("Label19".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「１. リハビリテーション」";
        else if ("Shape47".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「４．その他　○」";
        else if ("Shape44".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「３. 装着・使用医療機器等の操作援助・管理　○」";
        else if ("Shape45".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「２. 褥瘡の処置等　○」";
        else if ("Shape46".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「１. リハビリテーション　○」";
        else if ("Label18".equals(code)) obj = "要介護認定の状況　「５ ）」";
        else if ("Label17".equals(code)) obj = "要介護認定の状況　「４」";
        else if ("Label16".equals(code)) obj = "要介護認定の状況　「３」";
        else if ("Label15".equals(code)) obj = "要介護認定の状況　「２」";
        else if ("Label14".equals(code)) obj = "要介護認定の状況　「１」";
        else if ("Label10".equals(code)) obj = "生年月日・元号　「平」　見出し";
        else if ("Label8".equals(code)) obj = "生年月日・元号　「大」　見出し";
        else if ("Label7".equals(code)) obj = "生年月日・元号　「明｣　見出し";
        else if ("Shape27".equals(code)) obj = "日常生活自立度・寝たきり度　「自立　／」";
        else if ("Label9".equals(code)) obj = "生年月日・元号　「昭」　見出し";
        else if ("title".equals(code)) obj = "「訪問看護指示書」　見出し";
        else if ("Label1".equals(code)) obj = "「上記のとおり、指定訪問看護の実施を指示いたします。」　見出し";
        else if ("Label2".equals(code)) obj = "作成年月日　「平成  年  月  日」";
        else if ("Label3".equals(code)) obj = "訪問看護ステーション　「印」　見出し";
        else if ("Label4".equals(code)) obj = "「介護老人保健施設名」　見出し";
        else if ("Label5".equals(code)) obj = "「介護老人保健施設医師氏名」　見出し";
        else if ("Label6".equals(code)) obj = "訪問看護ステーション名";
        else if ("Label23".equals(code)) obj = "「要介護認定の状況」　見出し";
        else if ("Label24".equals(code)) obj = "「在宅患者訪問点滴注射に関する指示（投与薬剤・投与量・投与方法等）」　見出し";
        else if ("Grid13.h1.w2".equals(code)) obj = "介護老人保健施設名";
        else if ("Grid13.h2.w1".equals(code)) obj = "「住　　　所」　見出し";
        else if ("Grid13.h2.w2".equals(code)) obj = "介護老人保健施設住所";
        else if ("Grid13.h3.w1".equals(code)) obj = "「電　　　話」　見出し";
        else if ("Grid13.h3.w2".equals(code)) obj = "介護老人保健施設　電話番号";
        else if ("Grid13.h4.w1".equals(code)) obj = "「（ＦＡＸ）」　見出し";
        else if ("Grid13.h4.w2".equals(code)) obj = "介護老人保健施設　ＦＡＸ番号";
        else if ("Grid13.h5.w2".equals(code)) obj = "介護老人保健施設医師氏名";
        else if ("Grid8".equals(code)) obj = "日常生活　枠";
        else if ("Grid8.h1.w1".equals(code)) obj = "日常生活自立度　「日常生活」　見出し";
        else if ("Grid8.h1.w2".equals(code)) obj = "「寝たきり度」　見出し";
        else if ("Grid8.h1.w19".equals(code)) obj = " J1";
        else if ("Grid8.h1.w17".equals(code)) obj = " J2";
        else if ("Grid8.h1.w15".equals(code)) obj = " A1";
        else if ("Grid8.h1.w13".equals(code)) obj = " A2";
        else if ("Grid8.h1.w11".equals(code)) obj = " B1";
        else if ("Grid8.h1.w9".equals(code)) obj = " B2";
        else if ("Grid8.h1.w7".equals(code)) obj = " C1";
        else if ("Grid8.h1.w5".equals(code)) obj = " C2";
        else if ("Grid8.h2.w1".equals(code)) obj = "日常生活自立度　「自 立 度」　見出し";
        else if ("Grid8.h2.w2".equals(code)) obj = "「認知症の状況」　見出し";
        else if ("Grid8.h2.w19".equals(code)) obj = " I";
        else if ("Grid8.h2.w17".equals(code)) obj = " IIａ";
        else if ("Grid8.h2.w15".equals(code)) obj = " IIｂ";
        else if ("Grid8.h2.w13".equals(code)) obj = " IIIａ";
        else if ("Grid8.h2.w11".equals(code)) obj = " IIIｂ";
        else if ("Grid8.h2.w9".equals(code)) obj = " IV";
        else if ("Grid8.h2.w7".equals(code)) obj = " Ｍ";
        else if ("Grid8.h3.w19".equals(code)) obj = " 要支援";
        else if ("Grid8.h3.w17".equals(code)) obj = " 要介護";
        else if ("Grid10".equals(code)) obj = "留意事項及び指示事項　枠";
        else if ("Grid10.h1.w1".equals(code)) obj = "「留意事項及び指示事項」　見出し";
        else if ("Grid10.h13.w1".equals(code)) obj = "「 I 療養生活指導上の留意事項」　見出し";
        else if ("Grid10.h2.w2".equals(code)) obj = " I 療養生活指導上の留意事項";
        else if ("Grid10.h3.w1".equals(code)) obj = "「II」 　見出し";
        else if ("Grid10.h4.w2".equals(code)) obj = "１. リハビリテーション";
        else if ("Grid10.h6.w2".equals(code)) obj = "２. 褥瘡の処置等";
        else if ("Grid10.h8.w2".equals(code)) obj = "３. 装着・使用医療機器等の操作援助・管理";
        else if ("Grid10.h12.w2".equals(code)) obj = "４. その他";
        else if ("Grid10.h10.w2".equals(code)) obj = "在宅患者訪問点滴注射に関する指示（投与薬剤・投与量・投与方法等）";
        else if ("Grid1".equals(code)) obj = "訪問看護指示期間・点滴注射指示期間　枠";
        else if ("Grid1.h1.w14".equals(code)) obj = "「訪問看護指示期間」　見出し";
        else if ("Grid1.h1.w2".equals(code)) obj = "訪問看護指示期間　開始年";
        else if ("Grid1.h1.w3".equals(code)) obj = "訪問看護指示期間　開始年　見出し";
        else if ("Grid1.h1.w4".equals(code)) obj = "訪問看護指示期間　開始月";
        else if ("Grid1.h1.w5".equals(code)) obj = "訪問看護指示期間　開始月　見出し";
        else if ("Grid1.h1.w6".equals(code)) obj = "訪問看護指示期間　開始日";
        else if ("Grid1.h1.w7".equals(code)) obj = "訪問看護指示期間　開始日　見出し";
        else if ("Grid1.h1.w8".equals(code)) obj = "訪問看護指示期間　終了年";
        else if ("Grid1.h1.w9".equals(code)) obj = "訪問看護指示期間　終了年　見出し";
        else if ("Grid1.h1.w10".equals(code)) obj = "訪問看護指示期間　終了月";
        else if ("Grid1.h1.w11".equals(code)) obj = "訪問看護指示期間　終了月　見出し";
        else if ("Grid1.h1.w12".equals(code)) obj = "訪問看護指示期間　終了日";
        else if ("Grid1.h1.w13".equals(code)) obj = "訪問看護指示期間　終了日　見出し";
        else if ("Grid1.h2.w14".equals(code)) obj = "「点滴注射指示期間」　見出し";
        else if ("Grid1.h2.w2".equals(code)) obj = "点滴注射指示期間　開始年";
        else if ("Grid1.h2.w3".equals(code)) obj = "点滴注射指示期間　開始年　見出し";
        else if ("Grid1.h2.w4".equals(code)) obj = "点滴注射指示期間　開始月";
        else if ("Grid1.h2.w5".equals(code)) obj = "点滴注射指示期間　開始月　見出し";
        else if ("Grid1.h2.w6".equals(code)) obj = "点滴注射指示期間　開始日";
        else if ("Grid1.h2.w7".equals(code)) obj = "点滴注射指示期間　開始日　見出し";
        else if ("Grid1.h2.w8".equals(code)) obj = "点滴注射指示期間　終了年";
        else if ("Grid1.h2.w9".equals(code)) obj = "点滴注射指示期間　終了年　見出し";
        else if ("Grid1.h2.w10".equals(code)) obj = "点滴注射指示期間　終了月";
        else if ("Grid1.h2.w11".equals(code)) obj = "点滴注射指示期間　終了月　見出し";
        else if ("Grid1.h2.w12".equals(code)) obj = "点滴注射指示期間　終了日";
        else if ("Grid1.h2.w13".equals(code)) obj = "点滴注射指示期間　終了日　見出し";
        else if ("Grid2.h1.w1".equals(code)) obj = "「入所者氏名」　見出し";
        else if ("Grid2.h1.w2".equals(code)) obj = "入所者氏名";
        else if ("Grid2.h1.w3".equals(code)) obj = "「生年月日」";
        else if ("Grid2.h1.w5".equals(code)) obj = "生年月日・年";
        else if ("Grid2.h1.w6".equals(code)) obj = "生年月日・年　見出し";
        else if ("Grid2.h1.w7".equals(code)) obj = "生年月日・月";
        else if ("Grid2.h1.w8".equals(code)) obj = "生年月日・月　見出し";
        else if ("Grid2.h1.w9".equals(code)) obj = "生年月日・日";
        else if ("Grid2.h1.w10".equals(code)) obj = "生年月日・日　見出し";
        else if ("Grid2.h1.w12".equals(code)) obj = "歳）";
        else if ("Grid3.h1.w1".equals(code)) obj = "「入所者住所」　見出し";
        else if ("Grid3.h1.w3".equals(code)) obj = "「〒」　見出し";
        else if ("Grid3.h1.w4".equals(code)) obj = "入所者郵便番号";
        else if ("Grid3.h2.w4".equals(code)) obj = "入所者住所";
        else if ("Grid3.h3.w2".equals(code)) obj = "電話番号";
        else if ("Grid3.h3.w6".equals(code)) obj = "「電話」　見出し";
        else if ("Grid4.h1.w1".equals(code)) obj = "「主たる傷病名」　見出し";
        else if ("Grid4.h1.w2".equals(code)) obj = "主たる傷病名";
        else if ("Grid5.h1.w1".equals(code)) obj = "「現在の状況」　見出し";
        else if ("Grid6.h1.w1".equals(code)) obj = "「症状・治療状態」　見出し";
        else if ("Grid6.h1.w2".equals(code)) obj = "症状・治療状態";
        else if ("Grid7.h1.w1".equals(code)) obj = "投与中の薬剤の用法・用量・「投与中の」　見出し";
        else if ("Grid7.h1.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「１」　見出し";
        else if ("Grid7.h1.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「１」";
        else if ("Grid7.h1.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「２」　見出し";
        else if ("Grid7.h1.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「２」";
        else if ("Grid7.h2.w1".equals(code)) obj = "投与中の薬剤の用法・用量・「薬剤の用」";
        else if ("Grid7.h2.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「３」　見出し";
        else if ("Grid7.h2.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「３」";
        else if ("Grid7.h2.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「４」　見出し";
        else if ("Grid7.h2.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「４」";
        else if ("Grid7.h3.w1".equals(code)) obj = "投与中の薬剤の用法・用量・「法・用量」";
        else if ("Grid7.h3.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「５」　見出し";
        else if ("Grid7.h3.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「５」";
        else if ("Grid7.h3.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「６」　見出し";
        else if ("Grid7.h3.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「６」";
        else if ("Grid9".equals(code)) obj = "「装着・使用医療機器等」　枠";
        else if ("Grid9.h1.w3".equals(code)) obj = "１．自動腹膜灌流装置・「1」　見出し";
        else if ("Grid9.h1.w4".equals(code)) obj = "１．自動腹膜灌流装置・「自動腹膜灌流装置」　見出し";
        else if ("Grid9.h1.w7".equals(code)) obj = "２．透析液供給装置・「2」　見出し";
        else if ("Grid9.h1.w8".equals(code)) obj = "２．透析液供給装置・「透析液供給装置」　見出し";
        else if ("Grid9.h1.w15".equals(code)) obj = "３．酸素療法・「3」　見出し";
        else if ("Grid9.h1.w24".equals(code)) obj = "３．酸素療法・「酸素療法（」　見出し";
        else if ("Grid9.h1.w20".equals(code)) obj = "３．酸素療法";
        else if ("Grid9.h1.w21".equals(code)) obj = "３．酸素療法・「 l/min」　見出し";
        else if ("Grid9.h1.w22".equals(code)) obj = "３．酸素療法・「）」　見出し";
        else if ("Grid9.h2.w1".equals(code)) obj = "装着・使用医療機器等・「装着・使」　見出し";
        else if ("Grid9.h2.w3".equals(code)) obj = "４．吸引器・「4」　見出し";
        else if ("Grid9.h2.w4".equals(code)) obj = "４．吸引器・「吸引器」　見出し";
        else if ("Grid9.h2.w7".equals(code)) obj = "５．中心静脈栄養・「5」　見出し";
        else if ("Grid9.h2.w8".equals(code)) obj = "５．中心静脈栄養・「中心静脈栄養」　見出し";
        else if ("Grid9.h2.w15".equals(code)) obj = "６．輸液ポンプ・「6」　見出し";
        else if ("Grid9.h2.w24".equals(code)) obj = "６．輸液ポンプ・「輸液ポンプ」　見出し";
        else if ("Grid9.h3.w1".equals(code)) obj = "装着・使用医療機器等・「用医療機」　見出し";
        else if ("Grid9.h3.w3".equals(code)) obj = "７．経管栄養・「7」　見出し";
        else if ("Grid9.h3.w4".equals(code)) obj = "７．経管栄養・「経管栄養　　　（」　見出し";
        else if ("Grid9.h3.w23".equals(code)) obj = "７．経管栄養";
        else if ("Grid9.h3.w9".equals(code)) obj = "７．経管栄養・「：チューブサイズ」　見出し";
        else if ("Grid9.h3.w15".equals(code)) obj = "７．経管栄養・「：チューブサイズ」";
        else if ("Grid9.h3.w16".equals(code)) obj = "７．経管栄養・「、」　見出し";
        else if ("Grid9.h3.w18".equals(code)) obj = "７．経管栄養　日";
        else if ("Grid9.h3.w19".equals(code)) obj = "７．経管栄養・「日に1回交換」　見出し";
        else if ("Grid9.h3.w22".equals(code)) obj = "７．経管栄養・「）」　見出し";
        else if ("Grid9.h4.w1".equals(code)) obj = "装着・使用医療機器等・「 器等」　見出し";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete begin 【2012年度対応】訪問指示書の留置カテーテル部位追加
//        else if ("Grid9.h4.w3".equals(code)) obj = "８．留置カテーテル・「8」　見出し";
//        else if ("Grid9.h4.w4".equals(code)) obj = "８．留置カテーテル・「留置カテーテル（サイズ」　見出し";
//        else if ("Grid9.h4.w9".equals(code)) obj = "８．留置カテーテル　サイズ";
//        else if ("Grid9.h4.w13".equals(code)) obj = "８．留置カテーテル・「、」　見出し";
//        else if ("Grid9.h4.w18".equals(code)) obj = "８．留置カテーテル　日";
//        else if ("Grid9.h4.w19".equals(code)) obj = "８．留置カテーテル・「 日に1回交換」　見出し";
//        else if ("Grid9.h4.w22".equals(code)) obj = "８．留置カテーテル・「）」　見出し";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete end
        //[ID:0000689][Shin Fujihara] 2012/03/12 add begin 【2012年度対応】訪問指示書の留置カテーテル部位追加
        else if ("Grid9.h4.w3".equals(code)) obj = "８．留置カテーテル・「8」　見出し";
        else if ("Grid9.h4.w4".equals(code)) obj = "８．留置カテーテル・「留置カテーテル（部位：」　見出し";
        else if ("Grid9.h4.w23".equals(code)) obj = "８．留置カテーテル　部位";
        else if ("Grid9.h4.w12".equals(code)) obj = "８．留置カテーテル・「留置カテーテル（サイズ」　見出し";
        else if ("Grid9.h4.w15".equals(code)) obj = "８．留置カテーテル　サイズ";
        else if ("Grid9.h4.w17".equals(code)) obj = "８．留置カテーテル・「、」　見出し";
        else if ("Grid9.h4.w18".equals(code)) obj = "８．留置カテーテル　日";
        else if ("Grid9.h4.w19".equals(code)) obj = "８．留置カテーテル・「 日に1回交換」　見出し";
        else if ("Grid9.h4.w22".equals(code)) obj = "８．留置カテーテル・「）」　見出し";
        //[ID:0000689][Shin Fujihara] 2012/03/12 add end
        else if ("Grid9.h5.w1".equals(code)) obj = "装着・使用医療機器等・「 （番号に」　見出し";
        else if ("Grid9.h5.w3".equals(code)) obj = "９．人工呼吸器・「9」　見出し";
        else if ("Grid9.h5.w4".equals(code)) obj = "９．人工呼吸器・「人工呼吸器　　（」　見出し";
        else if ("Grid9.h5.w8".equals(code)) obj = "９．人工呼吸器　種類";
        else if ("Grid9.h5.w10".equals(code)) obj = "９．人工呼吸器・「：設定」　見出し";
        else if ("Grid9.h5.w6".equals(code)) obj = "９．人工呼吸器　設定";
        else if ("Grid9.h5.w22".equals(code)) obj = "９．人工呼吸器・「）」　見出し";
        else if ("Grid9.h6.w1".equals(code)) obj = "装着・使用医療機器等・「 ○印）」　見出し";
        else if ("Grid9.h6.w3".equals(code)) obj = "１０．気管カニューレ・「10｣　見出し";
        else if ("Grid9.h6.w4".equals(code)) obj = "１０．気管カニューレ・「気管カニューレ（サイズ」　見出し";
        else if ("Grid9.h6.w9".equals(code)) obj = "１０．気管カニューレ　サイズ";
        else if ("Grid9.h6.w11".equals(code)) obj = "１０．気管カニューレ・「）」　見出し";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete begin 【2012年度対応】訪問指示書のドレーン削除
//        else if ("Grid9.h6.w12".equals(code)) obj = "１１．ドレーン・「11」　見出し";
//        else if ("Grid9.h6.w13".equals(code)) obj = "１１．ドレーン・「ドレーン（部位：」　見出し";
//        else if ("Grid9.h6.w20".equals(code)) obj = "１１．ドレーン　部位";
//        else if ("Grid9.h6.w22".equals(code)) obj = "１１．ドレーン・「）」　見出し";
//        else if ("Grid9.h7.w3".equals(code)) obj = "１２．人工肛門・「12」　見出し";
//        else if ("Grid9.h7.w4".equals(code)) obj = "１２．人工肛門・「人工肛門」　見出し";
//        else if ("Grid9.h7.w5".equals(code)) obj = "１３．人工膀胱・「13」　見出し";
//        else if ("Grid9.h7.w23".equals(code)) obj = "１３．人工膀胱・「人工膀胱」　見出し";
//        else if ("Grid9.h7.w10".equals(code)) obj = "１４・その他・「14」　見出し";
//        else if ("Grid9.h7.w11".equals(code)) obj = "１４・その他・「その他（」　見出し";
//        else if ("Grid9.h7.w14".equals(code)) obj = "１４．その他";
//        else if ("Grid9.h7.w22".equals(code)) obj = "１４・その他・「）」　見出し";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete end
        //[ID:0000689][Shin Fujihara] 2012/03/12 add begin 【2012年度対応】訪問指示書のドレーン削除
        else if ("Grid9.h7.w3".equals(code)) obj = "１１．人工肛門・「11」　見出し";
        else if ("Grid9.h7.w4".equals(code)) obj = "１１．人工肛門・「人工肛門」　見出し";
        else if ("Grid9.h7.w5".equals(code)) obj = "１２．人工膀胱・「12」　見出し";
        else if ("Grid9.h7.w23".equals(code)) obj = "１２．人工膀胱・「人工膀胱」　見出し";
        else if ("Grid9.h7.w10".equals(code)) obj = "１３・その他・「13」　見出し";
        else if ("Grid9.h7.w11".equals(code)) obj = "１３・その他・「その他（」　見出し";
        else if ("Grid9.h7.w14".equals(code)) obj = "１３．その他";
        else if ("Grid9.h7.w22".equals(code)) obj = "１３・その他・「）」　見出し";
        //[ID:0000689][Shin Fujihara] 2012/03/12 add end
        else if ("Grid11.h1.w1".equals(code)) obj = "「緊急時の連絡先」　見出し";
        else if ("Grid11.h1.w2".equals(code)) obj = "緊急時の連絡先";
        else if ("Grid11.h2.w1".equals(code)) obj = "「不在時の対応法」　見出し";
        else if ("Grid11.h2.w2".equals(code)) obj = "不在時の対応法";
        else if ("Grid12.h1.w1".equals(code)) obj = "特記すべき留意事項・「特記すべき留意事項」　見出し";
        else if ("Grid12.h1.w6".equals(code)) obj = "特記すべき留意事項・「（注：薬の相互作用・副作用についての留意点、薬物アレルギーの既往等あれば記載して下さい。）」　見出し";
        else if ("Grid12.h2.w1".equals(code)) obj = "特記すべき留意事項";
        else if ("Grid12.h3.w1".equals(code)) obj = "他の訪問看護ステーションへの指示・「他の訪問看護ステーションへの指示」　見出し";
        else if ("Grid12.h4.w1".equals(code)) obj = "他の訪問看護ステーションへの指示・「（」　見出し ";
        else if ("Grid12.h4.w2".equals(code)) obj = "他の訪問看護ステーションへの指示・「無」　見出し";
        else if ("Grid12.h4.w4".equals(code)) obj = "他の訪問看護ステーションへの指示・「有」　見出し";
        else if ("Grid12.h4.w5".equals(code)) obj = "他の訪問看護ステーションへの指示・「：指定訪問看護ステーション名」　見出し";
        else if ("Grid12.h4.w6".equals(code)) obj = "他の訪問看護ステーション";
        else if ("Grid12.h4.w8".equals(code)) obj = "他の訪問看護ステーションへの指示・「 殿」　見出し";
        else if ("Grid12.h4.w7".equals(code)) obj = "他の訪問看護ステーションへの指示・「）」　見出し";
        
        //[ID:0000732][Shin Fujihara] 2012/04/20 add begin 【2012年度対応：訪問看護指示書】たん吸引指示追加
        else if ("Grid12.h6.w1".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「たんの吸引等実施のための訪問介護事業所への指示」　見出し";
        else if ("Grid12.h8.w1".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「（」　見出し ";
        else if ("Grid12.h8.w2".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「無」　見出し";
        else if ("Grid12.h8.w4".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「有」　見出し";
        else if ("Grid12.h8.w5".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「：訪問介護事業所名」　見出し";
        else if ("Grid12.h8.w6".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所";
        else if ("Grid12.h8.w8".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「 殿」　見出し";
        else if ("Grid12.h8.w7".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「）」　見出し";
        //[ID:0000732][Shin Fujihara] 2012/04/20 add end 【2012年度対応：訪問看護指示書】たん吸引指示追加
        
        //[ID:0000639][Shin Fujihara] 2011/03 add begin 「褥瘡の深さ」対応漏れ
        else if ("Label26".equals(code)) obj = "「褥瘡の深さ」　見出し";
        else if ("Grid15.h3.w22".equals(code)) obj = "褥瘡の深さ　「NPUAP分類」";
        else if ("Grid15.h3.w20".equals(code)) obj = "褥瘡の深さ・NPUAP分類　「Ⅲ度」";
        else if ("Grid15.h3.w17".equals(code)) obj = "褥瘡の深さ・NPUAP分類　「Ⅳ度」";
        else if ("Grid15.h3.w15".equals(code)) obj = "褥瘡の深さ　「DESIGN分類」";
        else if ("Grid15.h3.w13".equals(code)) obj = "褥瘡の深さ・DESIGN分類　「D3」";
        else if ("Grid15.h3.w11".equals(code)) obj = "褥瘡の深さ・DESIGN分類　「D4」";
        else if ("Grid15.h3.w9".equals(code)) obj = "褥瘡の深さ・DESIGN分類　「D5」";
        //[ID:0000639][Shin Fujihara] 2011/03 add end
        return obj;
    }

    /**
     * 請求書一覧の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatSeikyuIchitan(String code, Object obj){
        if ("keizoku12".equals(code)) obj = "明細12行目「継続」○印";
        else if ("sinki12".equals(code)) obj = "明細12行目「新規」○印";
        else if ("sisetu12".equals(code)) obj = "明細12行目「施設」○印";
        else if ("zaitaku12".equals(code)) obj = "明細12行目「在宅」○印";
        else if ("keizoku11".equals(code)) obj = "明細11行目「継続」○印";
        else if ("sinki11".equals(code)) obj = "明細11行目「新規」○印";
        else if ("sisetu11".equals(code)) obj = "明細11行目「施設」○印";
        else if ("zaitaku11".equals(code)) obj = "明細11行目「在宅」○印";
        else if ("keizoku10".equals(code)) obj = "明細10行目「継続」○印";
        else if ("sinki10".equals(code)) obj = "明細10行目「新規」○印";
        else if ("sisetu10".equals(code)) obj = "明細10行目「施設」○印";
        else if ("zaitaku10".equals(code)) obj = "明細10行目「在宅」○印";
        else if ("keizoku9".equals(code)) obj = "明細9行目「継続」○印";
        else if ("sinki9".equals(code)) obj = "明細9行目「新規」○印";
        else if ("sisetu9".equals(code)) obj = "明細9行目「施設」○印";
        else if ("zaitaku9".equals(code)) obj = "明細9行目「在宅」○印";
        else if ("keizoku8".equals(code)) obj = "明細8行目「継続」○印";
        else if ("sinki8".equals(code)) obj = "明細8行目「新規」○印";
        else if ("sisetu8".equals(code)) obj = "明細8行目「施設」○印";
        else if ("zaitaku8".equals(code)) obj = "明細8行目「在宅」○印";
        else if ("keizoku7".equals(code)) obj = "明細7行目「継続」○印";
        else if ("sinki7".equals(code)) obj = "明細7行目「新規」○印";
        else if ("sisetu7".equals(code)) obj = "明細7行目「施設」○印";
        else if ("zaitaku7".equals(code)) obj = "明細7行目「在宅」○印";
        else if ("keizoku6".equals(code)) obj = "明細6行目「継続」○印";
        else if ("sinki6".equals(code)) obj = "明細6行目「新規」○印";
        else if ("sisetu6".equals(code)) obj = "明細6行目「施設」○印";
        else if ("zaitaku6".equals(code)) obj = "明細6行目「在宅」○印";
        else if ("keizoku5".equals(code)) obj = "明細5行目「継続」○印";
        else if ("sinki5".equals(code)) obj = "明細5行目「新規」○印";
        else if ("sisetu5".equals(code)) obj = "明細5行目「施設」○印";
        else if ("zaitaku5".equals(code)) obj = "明細5行目「在宅」○印";
        else if ("keizoku4".equals(code)) obj = "明細4行目「継続」○印";
        else if ("sinki4".equals(code)) obj = "明細4行目「新規」○印";
        else if ("sisetu4".equals(code)) obj = "明細4行目「施設」○印";
        else if ("zaitaku4".equals(code)) obj = "明細4行目「在宅」○印";
        else if ("keizoku3".equals(code)) obj = "明細3行目「継続」○印";
        else if ("sinki3".equals(code)) obj = "明細3行目「新規」○印";
        else if ("sisetu3".equals(code)) obj = "明細3行目「施設」○印";
        else if ("zaitaku3".equals(code)) obj = "明細3行目「在宅」○印";
        else if ("keizoku2".equals(code)) obj = "明細2行目「継続」○印";
        else if ("sinki2".equals(code)) obj = "明細2行目「新規」○印";
        else if ("sisetu2".equals(code)) obj = "明細2行目「施設」○印";
        else if ("zaitaku2".equals(code)) obj = "明細2行目「在宅」○印";
        else if ("keizoku1".equals(code)) obj = "明細1行目「継続」○印";
        else if ("sinki1".equals(code)) obj = "明細1行目「新規」○印";
        else if ("sisetu1".equals(code)) obj = "明細1行目「施設」○印";
        else if ("zaitaku1".equals(code)) obj = "明細1行目「在宅」○印";
        else if ("pageNo".equals(code)) obj = "ページ番号";
        else if ("Label1".equals(code)) obj = "明細1行目「在宅」";
        else if ("Label2".equals(code)) obj = "明細1行目「・」1";
        else if ("Label3".equals(code)) obj = "明細1行目「施設」";
        else if ("Label4".equals(code)) obj = "明細1行目「新規」";
        else if ("Label5".equals(code)) obj = "明細1行目「継続」";
        else if ("Label6".equals(code)) obj = "明細1行目「・」2";
        else if ("Label8".equals(code)) obj = "明細2行目「在宅」";
        else if ("Label9".equals(code)) obj = "明細2行目「・」1";
        else if ("Label10".equals(code)) obj = "明細2行目「施設」";
        else if ("Label11".equals(code)) obj = "明細2行目「新規」";
        else if ("Label12".equals(code)) obj = "明細2行目「継続」";
        else if ("Label13".equals(code)) obj = "明細2行目「・」2";
        else if ("Label14".equals(code)) obj = "明細3行目「在宅」";
        else if ("Label15".equals(code)) obj = "明細3行目「・」1";
        else if ("Label16".equals(code)) obj = "明細3行目「施設」";
        else if ("Label17".equals(code)) obj = "明細3行目「新規」";
        else if ("Label18".equals(code)) obj = "明細3行目「継続」";
        else if ("Label19".equals(code)) obj = "明細3行目「・」2";
        else if ("Label20".equals(code)) obj = "明細4行目「在宅」";
        else if ("Label21".equals(code)) obj = "明細4行目「・」1";
        else if ("Label22".equals(code)) obj = "明細4行目「施設」";
        else if ("Label23".equals(code)) obj = "明細4行目「新規」";
        else if ("Label24".equals(code)) obj = "明細4行目「継続」";
        else if ("Label25".equals(code)) obj = "明細4行目「・」2";
        else if ("Label26".equals(code)) obj = "明細5行目「在宅」";
        else if ("Label27".equals(code)) obj = "明細5行目「・」1";
        else if ("Label28".equals(code)) obj = "明細5行目「施設」";
        else if ("Label29".equals(code)) obj = "明細5行目「新規」";
        else if ("Label30".equals(code)) obj = "明細5行目「継続」";
        else if ("Label31".equals(code)) obj = "明細5行目「・」2";
        else if ("Label32".equals(code)) obj = "明細6行目「在宅」";
        else if ("Label33".equals(code)) obj = "明細6行目「・」1";
        else if ("Label34".equals(code)) obj = "明細6行目「施設」";
        else if ("Label35".equals(code)) obj = "明細6行目「新規」";
        else if ("Label36".equals(code)) obj = "明細6行目「継続」";
        else if ("Label37".equals(code)) obj = "明細6行目「・」2";
        else if ("Label38".equals(code)) obj = "明細7行目「在宅」";
        else if ("Label39".equals(code)) obj = "明細7行目「・」1";
        else if ("Label40".equals(code)) obj = "明細7行目「施設」";
        else if ("Label41".equals(code)) obj = "明細7行目「新規」";
        else if ("Label42".equals(code)) obj = "明細7行目「継続」";
        else if ("Label43".equals(code)) obj = "明細7行目「・」2";
        else if ("Label44".equals(code)) obj = "明細8行目「在宅」";
        else if ("Label45".equals(code)) obj = "明細8行目「・」1";
        else if ("Label46".equals(code)) obj = "明細8行目「施設」";
        else if ("Label47".equals(code)) obj = "明細8行目「新規」";
        else if ("Label48".equals(code)) obj = "明細8行目「継続」";
        else if ("Label49".equals(code)) obj = "明細8行目「・」2";
        else if ("Label50".equals(code)) obj = "明細9行目「在宅」";
        else if ("Label51".equals(code)) obj = "明細9行目「・」1";
        else if ("Label52".equals(code)) obj = "明細9行目「施設」";
        else if ("Label53".equals(code)) obj = "明細9行目「新規」";
        else if ("Label54".equals(code)) obj = "明細9行目「継続」";
        else if ("Label55".equals(code)) obj = "明細9行目「・」2";
        else if ("Label56".equals(code)) obj = "明細10行目「在宅」";
        else if ("Label57".equals(code)) obj = "明細10行目「・」1";
        else if ("Label58".equals(code)) obj = "明細10行目「施設」";
        else if ("Label59".equals(code)) obj = "明細10行目「新規」";
        else if ("Label60".equals(code)) obj = "明細10行目「継続」";
        else if ("Label61".equals(code)) obj = "明細10行目「・」2";
        else if ("Label62".equals(code)) obj = "明細11行目「在宅」";
        else if ("Label63".equals(code)) obj = "明細11行目「・」1";
        else if ("Label64".equals(code)) obj = "明細11行目「施設」";
        else if ("Label65".equals(code)) obj = "明細11行目「新規」";
        else if ("Label66".equals(code)) obj = "明細11行目「継続」";
        else if ("Label67".equals(code)) obj = "明細11行目「・」2";
        else if ("Label68".equals(code)) obj = "明細12行目「在宅」";
        else if ("Label69".equals(code)) obj = "明細12行目「・」1";
        else if ("Label70".equals(code)) obj = "明細12行目「施設」";
        else if ("Label71".equals(code)) obj = "明細12行目「新規」";
        else if ("Label72".equals(code)) obj = "明細12行目「継続」";
        else if ("Label73".equals(code)) obj = "明細12行目「・」2";
        else if ("table".equals(code)) obj = "一覧";
        else if ("table.h1.w2".equals(code)) obj = "明細1行目「被保険者番号」見出し";
        else if ("table.h1.w3".equals(code)) obj = "明細1行目「被保険者番号」内容";
        else if ("table.h1.w4".equals(code)) obj = "明細1行目「意見書作成日」見出し";
        else if ("table.h1.w5".equals(code)) obj = "明細1行目「意見書作成日」内容";
        else if ("table.h2.w4".equals(code)) obj = "明細1行目「意見書送付日」見出し";
        else if ("table.h2.w5".equals(code)) obj = "明細1行目「意見書送付日」内容";
        else if ("table.h4.w2".equals(code)) obj = "明細1行目「ふりがな」見出し";
        else if ("table.h4.w3".equals(code)) obj = "明細1行目「ふりがな」内容";
        else if ("table.h4.w4".equals(code)) obj = "明細1行目「種別」見出し";
        else if ("table.h4.w5".equals(code)) obj = "明細1行目「種別」内容";
        else if ("table.h5.w2".equals(code)) obj = "明細1行目「被保険者氏名」見出し";
        else if ("table.h5.w3".equals(code)) obj = "明細1行目「被保険者氏名」内容";
        else if ("table.h5.w4".equals(code)) obj = "明細1行目「意見書作成料」見出し";
        else if ("table.h5.w5".equals(code)) obj = "明細1行目「意見書作成料」内容";
        else if ("table.h5.w6".equals(code)) obj = "明細1行目「意見書作成料」単位";
        else if ("table.h6.w4".equals(code)) obj = "明細1行目「診察・検査費用」見出し";
        else if ("table.h6.w5".equals(code)) obj = "明細1行目「診察・検査費用」内容";
        else if ("table.h6.w6".equals(code)) obj = "明細1行目「診察・検査費用」単位";
        else if ("table.h7.w2".equals(code)) obj = "明細2行目「被保険者番号」見出し";
        else if ("table.h7.w3".equals(code)) obj = "明細2行目「被保険者番号」内容";
        else if ("table.h7.w4".equals(code)) obj = "明細2行目「意見書作成日」見出し";
        else if ("table.h7.w5".equals(code)) obj = "明細2行目「意見書作成日」内容";
        else if ("table.h8.w4".equals(code)) obj = "明細2行目「意見書送付日」見出し";
        else if ("table.h8.w5".equals(code)) obj = "明細2行目「意見書送付日」内容";
        else if ("table.h10.w2".equals(code)) obj = "明細2行目「ふりがな」見出し";
        else if ("table.h10.w3".equals(code)) obj = "明細2行目「ふりがな」内容";
        else if ("table.h10.w4".equals(code)) obj = "明細2行目「種別」見出し";
        else if ("table.h10.w5".equals(code)) obj = "明細2行目「種別」内容";
        else if ("table.h11.w2".equals(code)) obj = "明細2行目「被保険者氏名」見出し";
        else if ("table.h11.w3".equals(code)) obj = "明細2行目「被保険者氏名」内容";
        else if ("table.h11.w4".equals(code)) obj = "明細2行目「意見書作成料」見出し";
        else if ("table.h11.w5".equals(code)) obj = "明細2行目「意見書作成料」内容";
        else if ("table.h11.w6".equals(code)) obj = "明細2行目「意見書作成料」単位";
        else if ("table.h12.w4".equals(code)) obj = "明細2行目「診察・検査費用」見出し";
        else if ("table.h12.w5".equals(code)) obj = "明細2行目「診察・検査費用」内容";
        else if ("table.h12.w6".equals(code)) obj = "明細2行目「診察・検査費用」単位";
        else if ("table.h13.w2".equals(code)) obj = "明細3行目「被保険者番号」見出し";
        else if ("table.h13.w3".equals(code)) obj = "明細3行目「被保険者番号」内容";
        else if ("table.h13.w4".equals(code)) obj = "明細3行目「意見書作成日」見出し";
        else if ("table.h13.w5".equals(code)) obj = "明細3行目「意見書作成日」内容";
        else if ("table.h14.w4".equals(code)) obj = "明細3行目「意見書送付日」見出し";
        else if ("table.h14.w5".equals(code)) obj = "明細3行目「意見書送付日」内容";
        else if ("table.h16.w2".equals(code)) obj = "明細3行目「ふりがな」見出し";
        else if ("table.h16.w3".equals(code)) obj = "明細3行目「ふりがな」内容";
        else if ("table.h16.w4".equals(code)) obj = "明細3行目「種別」見出し";
        else if ("table.h16.w5".equals(code)) obj = "明細3行目「種別」内容";
        else if ("table.h17.w2".equals(code)) obj = "明細3行目「被保険者氏名」見出し";
        else if ("table.h17.w3".equals(code)) obj = "明細3行目「被保険者氏名」内容";
        else if ("table.h17.w4".equals(code)) obj = "明細3行目「意見書作成料」見出し";
        else if ("table.h17.w5".equals(code)) obj = "明細3行目「意見書作成料」内容";
        else if ("table.h17.w6".equals(code)) obj = "明細3行目「意見書作成料」単位";
        else if ("table.h18.w4".equals(code)) obj = "明細3行目「診察・検査費用」見出し";
        else if ("table.h18.w5".equals(code)) obj = "明細3行目「診察・検査費用」内容";
        else if ("table.h18.w6".equals(code)) obj = "明細3行目「診察・検査費用」単位";
        else if ("table.h19.w2".equals(code)) obj = "明細4行目「被保険者番号」見出し";
        else if ("table.h19.w3".equals(code)) obj = "明細4行目「被保険者番号」内容";
        else if ("table.h19.w4".equals(code)) obj = "明細4行目「意見書作成日」見出し";
        else if ("table.h19.w5".equals(code)) obj = "明細4行目「意見書作成日」内容";
        else if ("table.h20.w4".equals(code)) obj = "明細4行目「意見書送付日」見出し";
        else if ("table.h20.w5".equals(code)) obj = "明細4行目「意見書送付日」内容";
        else if ("table.h22.w2".equals(code)) obj = "明細4行目「ふりがな」見出し";
        else if ("table.h22.w3".equals(code)) obj = "明細4行目「ふりがな」内容";
        else if ("table.h22.w4".equals(code)) obj = "明細4行目「種別」見出し";
        else if ("table.h22.w5".equals(code)) obj = "明細4行目「種別」内容";
        else if ("table.h23.w2".equals(code)) obj = "明細4行目「被保険者氏名」見出し";
        else if ("table.h23.w3".equals(code)) obj = "明細4行目「被保険者氏名」内容";
        else if ("table.h23.w4".equals(code)) obj = "明細4行目「意見書作成料」見出し";
        else if ("table.h23.w5".equals(code)) obj = "明細4行目「意見書作成料」内容";
        else if ("table.h23.w6".equals(code)) obj = "明細4行目「意見書作成料」単位";
        else if ("table.h24.w4".equals(code)) obj = "明細4行目「診察・検査費用」見出し";
        else if ("table.h24.w5".equals(code)) obj = "明細4行目「診察・検査費用」内容";
        else if ("table.h24.w6".equals(code)) obj = "明細4行目「診察・検査費用」単位";
        else if ("table.h25.w2".equals(code)) obj = "明細5行目「被保険者番号」見出し";
        else if ("table.h25.w3".equals(code)) obj = "明細5行目「被保険者番号」内容";
        else if ("table.h25.w4".equals(code)) obj = "明細5行目「意見書作成日」見出し";
        else if ("table.h25.w5".equals(code)) obj = "明細5行目「意見書作成日」内容";
        else if ("table.h26.w4".equals(code)) obj = "明細5行目「意見書送付日」見出し";
        else if ("table.h26.w5".equals(code)) obj = "明細5行目「意見書送付日」内容";
        else if ("table.h28.w2".equals(code)) obj = "明細5行目「ふりがな」見出し";
        else if ("table.h28.w3".equals(code)) obj = "明細5行目「ふりがな」内容";
        else if ("table.h28.w4".equals(code)) obj = "明細5行目「種別」見出し";
        else if ("table.h28.w5".equals(code)) obj = "明細5行目「種別」内容";
        else if ("table.h29.w2".equals(code)) obj = "明細5行目「被保険者氏名」見出し";
        else if ("table.h29.w3".equals(code)) obj = "明細5行目「被保険者氏名」内容";
        else if ("table.h29.w4".equals(code)) obj = "明細5行目「意見書作成料」見出し";
        else if ("table.h29.w5".equals(code)) obj = "明細5行目「意見書作成料」内容";
        else if ("table.h29.w6".equals(code)) obj = "明細5行目「意見書作成料」単位";
        else if ("table.h30.w4".equals(code)) obj = "明細5行目「診察・検査費用」見出し";
        else if ("table.h30.w5".equals(code)) obj = "明細5行目「診察・検査費用」内容";
        else if ("table.h30.w6".equals(code)) obj = "明細5行目「診察・検査費用」単位";
        else if ("table.h31.w2".equals(code)) obj = "明細6行目「被保険者番号」見出し";
        else if ("table.h31.w3".equals(code)) obj = "明細6行目「被保険者番号」内容";
        else if ("table.h31.w4".equals(code)) obj = "明細6行目「意見書作成日」見出し";
        else if ("table.h31.w5".equals(code)) obj = "明細6行目「意見書作成日」内容";
        else if ("table.h32.w4".equals(code)) obj = "明細6行目「意見書送付日」見出し";
        else if ("table.h32.w5".equals(code)) obj = "明細6行目「意見書送付日」内容";
        else if ("table.h34.w2".equals(code)) obj = "明細6行目「ふりがな」見出し";
        else if ("table.h34.w3".equals(code)) obj = "明細6行目「ふりがな」内容";
        else if ("table.h34.w4".equals(code)) obj = "明細6行目「種別」見出し";
        else if ("table.h34.w5".equals(code)) obj = "明細6行目「種別」内容";
        else if ("table.h35.w2".equals(code)) obj = "明細6行目「被保険者氏名」見出し";
        else if ("table.h35.w3".equals(code)) obj = "明細6行目「被保険者氏名」内容";
        else if ("table.h35.w4".equals(code)) obj = "明細6行目「意見書作成料」見出し";
        else if ("table.h35.w5".equals(code)) obj = "明細6行目「意見書作成料」内容";
        else if ("table.h35.w6".equals(code)) obj = "明細6行目「意見書作成料」単位";
        else if ("table.h36.w4".equals(code)) obj = "明細6行目「診察・検査費用」見出し";
        else if ("table.h36.w5".equals(code)) obj = "明細6行目「診察・検査費用」内容";
        else if ("table.h36.w6".equals(code)) obj = "明細6行目「診察・検査費用」単位";
        else if ("table.h37.w2".equals(code)) obj = "明細7行目「被保険者番号」見出し";
        else if ("table.h37.w3".equals(code)) obj = "明細7行目「被保険者番号」内容";
        else if ("table.h37.w4".equals(code)) obj = "明細7行目「意見書作成日」見出し";
        else if ("table.h37.w5".equals(code)) obj = "明細7行目「意見書作成日」内容";
        else if ("table.h38.w4".equals(code)) obj = "明細7行目「意見書送付日」見出し";
        else if ("table.h38.w5".equals(code)) obj = "明細7行目「意見書送付日」内容";
        else if ("table.h40.w2".equals(code)) obj = "明細7行目「ふりがな」見出し";
        else if ("table.h40.w3".equals(code)) obj = "明細7行目「ふりがな」内容";
        else if ("table.h40.w4".equals(code)) obj = "明細7行目「種別」見出し";
        else if ("table.h40.w5".equals(code)) obj = "明細7行目「種別」内容";
        else if ("table.h41.w2".equals(code)) obj = "明細7行目「被保険者氏名」見出し";
        else if ("table.h41.w3".equals(code)) obj = "明細7行目「被保険者氏名」内容";
        else if ("table.h41.w4".equals(code)) obj = "明細7行目「意見書作成料」見出し";
        else if ("table.h41.w5".equals(code)) obj = "明細7行目「意見書作成料」内容";
        else if ("table.h41.w6".equals(code)) obj = "明細7行目「意見書作成料」単位";
        else if ("table.h42.w4".equals(code)) obj = "明細7行目「診察・検査費用」見出し";
        else if ("table.h42.w5".equals(code)) obj = "明細7行目「診察・検査費用」内容";
        else if ("table.h42.w6".equals(code)) obj = "明細7行目「診察・検査費用」単位";
        else if ("table.h43.w2".equals(code)) obj = "明細8行目「被保険者番号」見出し";
        else if ("table.h43.w3".equals(code)) obj = "明細8行目「被保険者番号」内容";
        else if ("table.h43.w4".equals(code)) obj = "明細8行目「意見書作成日」見出し";
        else if ("table.h43.w5".equals(code)) obj = "明細8行目「意見書作成日」内容";
        else if ("table.h44.w4".equals(code)) obj = "明細8行目「意見書送付日」見出し";
        else if ("table.h44.w5".equals(code)) obj = "明細8行目「意見書送付日」内容";
        else if ("table.h46.w2".equals(code)) obj = "明細8行目「ふりがな」見出し";
        else if ("table.h46.w3".equals(code)) obj = "明細8行目「ふりがな」内容";
        else if ("table.h46.w4".equals(code)) obj = "明細8行目「種別」見出し";
        else if ("table.h46.w5".equals(code)) obj = "明細8行目「種別」内容";
        else if ("table.h47.w2".equals(code)) obj = "明細8行目「被保険者氏名」見出し";
        else if ("table.h47.w3".equals(code)) obj = "明細8行目「被保険者氏名」内容";
        else if ("table.h47.w4".equals(code)) obj = "明細8行目「意見書作成料」見出し";
        else if ("table.h47.w5".equals(code)) obj = "明細8行目「意見書作成料」内容";
        else if ("table.h47.w6".equals(code)) obj = "明細8行目「意見書作成料」単位";
        else if ("table.h48.w4".equals(code)) obj = "明細8行目「診察・検査費用」見出し";
        else if ("table.h48.w5".equals(code)) obj = "明細8行目「診察・検査費用」内容";
        else if ("table.h48.w6".equals(code)) obj = "明細8行目「診察・検査費用」単位";
        else if ("table.h49.w2".equals(code)) obj = "明細9行目「被保険者番号」見出し";
        else if ("table.h49.w3".equals(code)) obj = "明細9行目「被保険者番号」内容";
        else if ("table.h49.w4".equals(code)) obj = "明細9行目「意見書作成日」見出し";
        else if ("table.h49.w5".equals(code)) obj = "明細9行目「意見書作成日」内容";
        else if ("table.h50.w4".equals(code)) obj = "明細9行目「意見書送付日」見出し";
        else if ("table.h50.w5".equals(code)) obj = "明細9行目「意見書送付日」内容";
        else if ("table.h52.w2".equals(code)) obj = "明細9行目「ふりがな」見出し";
        else if ("table.h52.w3".equals(code)) obj = "明細9行目「ふりがな」内容";
        else if ("table.h52.w4".equals(code)) obj = "明細9行目「種別」見出し";
        else if ("table.h52.w5".equals(code)) obj = "明細9行目「種別」内容";
        else if ("table.h53.w2".equals(code)) obj = "明細9行目「被保険者氏名」見出し";
        else if ("table.h53.w3".equals(code)) obj = "明細9行目「被保険者氏名」内容";
        else if ("table.h53.w4".equals(code)) obj = "明細9行目「意見書作成料」見出し";
        else if ("table.h53.w5".equals(code)) obj = "明細9行目「意見書作成料」内容";
        else if ("table.h53.w6".equals(code)) obj = "明細9行目「意見書作成料」単位";
        else if ("table.h54.w4".equals(code)) obj = "明細9行目「診察・検査費用」見出し";
        else if ("table.h54.w5".equals(code)) obj = "明細9行目「診察・検査費用」内容";
        else if ("table.h54.w6".equals(code)) obj = "明細9行目「診察・検査費用」単位";
        else if ("table.h55.w2".equals(code)) obj = "明細10行目「被保険者番号」見出し";
        else if ("table.h55.w3".equals(code)) obj = "明細10行目「被保険者番号」内容";
        else if ("table.h55.w4".equals(code)) obj = "明細10行目「意見書作成日」見出し";
        else if ("table.h55.w5".equals(code)) obj = "明細10行目「意見書作成日」内容";
        else if ("table.h56.w4".equals(code)) obj = "明細10行目「意見書送付日」見出し";
        else if ("table.h56.w5".equals(code)) obj = "明細10行目「意見書送付日」内容";
        else if ("table.h58.w2".equals(code)) obj = "明細10行目「ふりがな」見出し";
        else if ("table.h58.w3".equals(code)) obj = "明細10行目「ふりがな」内容";
        else if ("table.h58.w4".equals(code)) obj = "明細10行目「種別」見出し";
        else if ("table.h58.w5".equals(code)) obj = "明細10行目「種別」内容";
        else if ("table.h59.w2".equals(code)) obj = "明細10行目「被保険者氏名」見出し";
        else if ("table.h59.w3".equals(code)) obj = "明細10行目「被保険者氏名」内容";
        else if ("table.h59.w4".equals(code)) obj = "明細10行目「意見書作成料」見出し";
        else if ("table.h59.w5".equals(code)) obj = "明細10行目「意見書作成料」内容";
        else if ("table.h59.w6".equals(code)) obj = "明細10行目「意見書作成料」単位";
        else if ("table.h60.w4".equals(code)) obj = "明細10行目「診察・検査費用」見出し";
        else if ("table.h60.w5".equals(code)) obj = "明細10行目「診察・検査費用」内容";
        else if ("table.h60.w6".equals(code)) obj = "明細10行目「診察・検査費用」単位";
        else if ("table.h61.w2".equals(code)) obj = "明細11行目「被保険者番号」見出し";
        else if ("table.h61.w3".equals(code)) obj = "明細11行目「被保険者番号」内容";
        else if ("table.h61.w4".equals(code)) obj = "明細11行目「意見書作成日」見出し";
        else if ("table.h61.w5".equals(code)) obj = "明細11行目「意見書作成日」内容";
        else if ("table.h62.w4".equals(code)) obj = "明細11行目「意見書送付日」見出し";
        else if ("table.h62.w5".equals(code)) obj = "明細11行目「意見書送付日」内容";
        else if ("table.h64.w2".equals(code)) obj = "明細11行目「ふりがな」見出し";
        else if ("table.h64.w3".equals(code)) obj = "明細11行目「ふりがな」内容";
        else if ("table.h64.w4".equals(code)) obj = "明細11行目「種別」見出し";
        else if ("table.h64.w5".equals(code)) obj = "明細11行目「種別」内容";
        else if ("table.h65.w2".equals(code)) obj = "明細11行目「被保険者氏名」見出し";
        else if ("table.h65.w3".equals(code)) obj = "明細11行目「被保険者氏名」内容";
        else if ("table.h65.w4".equals(code)) obj = "明細11行目「意見書作成料」見出し";
        else if ("table.h65.w5".equals(code)) obj = "明細11行目「意見書作成料」内容";
        else if ("table.h65.w6".equals(code)) obj = "明細11行目「意見書作成料」単位";
        else if ("table.h66.w4".equals(code)) obj = "明細11行目「診察・検査費用」見出し";
        else if ("table.h66.w5".equals(code)) obj = "明細11行目「診察・検査費用」内容";
        else if ("table.h66.w6".equals(code)) obj = "明細11行目「診察・検査費用」単位";
        else if ("table.h67.w2".equals(code)) obj = "明細12行目「被保険者番号」見出し";
        else if ("table.h67.w3".equals(code)) obj = "明細12行目「被保険者番号」内容";
        else if ("table.h67.w4".equals(code)) obj = "明細12行目「意見書作成日」見出し";
        else if ("table.h67.w5".equals(code)) obj = "明細12行目「意見書作成日」内容";
        else if ("table.h68.w4".equals(code)) obj = "明細12行目「意見書送付日」見出し";
        else if ("table.h68.w5".equals(code)) obj = "明細12行目「意見書送付日」内容";
        else if ("table.h70.w2".equals(code)) obj = "明細12行目「ふりがな」見出し";
        else if ("table.h70.w3".equals(code)) obj = "明細12行目「ふりがな」内容";
        else if ("table.h70.w4".equals(code)) obj = "明細12行目「種別」見出し";
        else if ("table.h70.w5".equals(code)) obj = "明細12行目「種別」内容";
        else if ("table.h71.w2".equals(code)) obj = "明細12行目「被保険者氏名」見出し";
        else if ("table.h71.w3".equals(code)) obj = "明細12行目「被保険者氏名」内容";
        else if ("table.h71.w4".equals(code)) obj = "明細12行目「意見書作成料」見出し";
        else if ("table.h71.w5".equals(code)) obj = "明細12行目「意見書作成料」内容";
        else if ("table.h71.w6".equals(code)) obj = "明細12行目「意見書作成料」単位";
        else if ("table.h72.w4".equals(code)) obj = "明細12行目「診察・検査費用」見出し";
        else if ("table.h72.w5".equals(code)) obj = "明細12行目「診察・検査費用」内容";
        else if ("table.h72.w6".equals(code)) obj = "明細12行目「診察・検査費用」単位";
        else if ("atena".equals(code)) obj = "宛名";
        else if ("atena.h1.name".equals(code)) obj = "宛名内容";
        else if ("dateRange".equals(code)) obj = "出力範囲日付";
        else if ("dateRange.h1.fromY".equals(code)) obj = "出力範囲日付「開始年」内容";
        else if ("dateRange.h1.fromM".equals(code)) obj = "出力範囲日付「開始月」内容";
        else if ("dateRange.h1.fromD".equals(code)) obj = "出力範囲日付「開始日」内容";
        else if ("dateRange.h1.w10".equals(code)) obj = "出力範囲日付「終了年」内容";
        else if ("dateRange.h1.w12".equals(code)) obj = "出力範囲日付「終了月」内容";
        else if ("dateRange.h1.w14".equals(code)) obj = "出力範囲日付「終了日」内容";
        return obj;
    }

    /**
     * 請求書合計の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatSeikyuIchiranTotal(String code, Object obj){
        if ("pageNo".equals(code)) obj = "ページ番号";
        else if ("Label1".equals(code)) obj = "「請求件数」";
        else if ("atena".equals(code)) obj = "請求先保険者情報";
        else if ("atena.h1.name".equals(code)) obj = "請求先保険者名";
        else if ("dateRange".equals(code)) obj = "出力範囲日付情報";
        else if ("dateRange.h1.fromY".equals(code)) obj = "出力範囲日付・開始日・年";
        else if ("dateRange.h1.w2".equals(code)) obj = "「年」　1";
        else if ("dateRange.h1.fromM".equals(code)) obj = "出力範囲日付・開始日・月";
        else if ("dateRange.h1.w4".equals(code)) obj = "「月」　2";
        else if ("dateRange.h1.fromD".equals(code)) obj = "出力範囲日付・開始日・日";
        else if ("dateRange.h1.w6".equals(code)) obj = "「日」　3";
        else if ("dateRange.h1.w8".equals(code)) obj = "「から」";
        else if ("dateRange.h1.toY".equals(code)) obj = "出力範囲日付・終了日・年";
        else if ("dateRange.h1.w10".equals(code)) obj = "「年」　2";
        else if ("dateRange.h1.toM".equals(code)) obj = "出力範囲日付・終了日・月";
        else if ("dateRange.h1.w12".equals(code)) obj = "「月」　2";
        else if ("dateRange.h1.toD".equals(code)) obj = "出力範囲日付・終了日・日";
        else if ("dateRange.h1.w14".equals(code)) obj = "「日」　2";
        else if ("totalCost".equals(code)) obj = "金額情報";
        else if ("totalCost.h1.w1".equals(code)) obj = "「意見書作成料」";
        else if ("totalCost.h1.cost".equals(code)) obj = "金額情報・意見書作成料";
        else if ("totalCost.h1.w2".equals(code)) obj = "「円」　1";
        else if ("totalCost.h2.w1".equals(code)) obj = "「診察・検査費用」";
        else if ("totalCost.h2.cost".equals(code)) obj = "金額情報・診察・検査費用";
        else if ("totalCost.h2.w2".equals(code)) obj = "「円」　2";
        else if ("totalCost.h3.w1".equals(code)) obj = "「消費税の総額」";
        else if ("totalCost.h3.cost".equals(code)) obj = "金額情報・消費税の総額";
        else if ("totalCost.h3.w2".equals(code)) obj = "「円」　3";
        else if ("totalCost.h4.w1".equals(code)) obj = "「合計請求金額」";
        else if ("totalCost.h4.cost".equals(code)) obj = "金額情報・合計請求金額";
        else if ("totalCost.h4.w2".equals(code)) obj = "「円」　4";
        else if ("totalCount".equals(code)) obj = "請求件数情報";
        else if ("totalCount.h1.count".equals(code)) obj = "請求件数";
        else if ("totalCount.h1.w3".equals(code)) obj = "「件」";
        else if ("bank".equals(code)) obj = "金融機関情報";
        else if ("bank.h1.w1".equals(code)) obj = "「振込先金融機関」";
        else if ("bank.h2.w2".equals(code)) obj = "「金融機関名」";
        else if ("bank.h2.w3".equals(code)) obj = "金融機関情報・振込先金融機関名";
        else if ("bank.h3.w2".equals(code)) obj = "「支店名」";
        else if ("bank.h3.w3".equals(code)) obj = "金融機関情報・支店名";
        else if ("bank.h4.w2".equals(code)) obj = "口座種類";
        else if ("bank.h4.w3".equals(code)) obj = "金融機関情報・口座種類";
        else if ("bank.h5.w2".equals(code)) obj = "口座番号";
        else if ("bank.h5.w3".equals(code)) obj = "金融機関情報・口座番号";
        else if ("bank.h6.w2".equals(code)) obj = "名義人";
        else if ("bank.h6.w3".equals(code)) obj = "金融機関情報・名義人";
        return obj;
    }

    /**
     * 主治医意見書作成料・検査料請求(総括)書の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatSoukatsusho(String code, Object obj){
        if ("dateRange".equals(code)) obj = "請求対象期間　枠";
        else if ("dateRange.h1.fromY".equals(code)) obj = "請求対象期間　開始年";
        else if ("dateRange.h1.w2".equals(code)) obj = "請求対象期間　開始年見出し";
        else if ("dateRange.h1.fromM".equals(code)) obj = "請求対象期間　開始月";
        else if ("dateRange.h1.w4".equals(code)) obj = "請求対象期間　開始月見出し";
        else if ("dateRange.h1.fromD".equals(code)) obj = "請求対象期間　開始日";
        else if ("dateRange.h1.w6".equals(code)) obj = "請求対象期間　開始日見出し";
        else if ("dateRange.h1.w8".equals(code)) obj = "請求対象期間　から";
        else if ("dateRange.h1.toY".equals(code)) obj = "請求対象期間　終了年号";
        else if ("dateRange.h1.w11".equals(code)) obj = "請求対象期間　終了年号見出し";
        else if ("dateRange.h1.toM".equals(code)) obj = "請求対象期間　終了月";
        else if ("dateRange.h1.w13".equals(code)) obj = "請求対象期間　終了月見出し";
        else if ("dateRange.h1.toD".equals(code)) obj = "請求対象期間　終了日";
        else if ("dateRange.h1.w15".equals(code)) obj = "請求対象期間　終了日見出し";
        else if ("dateRange.h1.w16".equals(code)) obj = "請求対象期間　終了見出し";
        else if ("Shape1".equals(code)) obj = "請求対象期間　下線";
        else if ("lblTitle".equals(code)) obj = "介護保険 主治医意見書作成料・検査料請求(総括)書　タイトル";
        else if ("prtDate".equals(code)) obj = "作成年月日　枠";
        else if ("prtDate.h1.era".equals(code)) obj = "作成年月日　元号";
        else if ("prtDate.h1.y".equals(code)) obj = "作成年月日　年";
        else if ("prtDate.h1.w3".equals(code)) obj = "作成年月日　年見出し";
        else if ("prtDate.h1.m".equals(code)) obj = "作成年月日　月";
        else if ("prtDate.h1.w5".equals(code)) obj = "作成年月日　月見出し";
        else if ("prtDate.h1.d".equals(code)) obj = "作成年月日　日";
        else if ("prtDate.h1.w7".equals(code)) obj = "作成年月日　日見出し";
        else if ("atena".equals(code)) obj = "保険者　宛名　枠";
        else if ("atena.h1.name".equals(code)) obj = "保険者　宛名";
        else if ("hospAdd".equals(code)) obj = "医療機関情報　枠";
        else if ("hospAdd.address.w1".equals(code)) obj = "医療機関住所　見出し";
        else if ("hospAdd.address.w2".equals(code)) obj = "医療機関住所";
        else if ("hospAdd.name.w1".equals(code)) obj = "医療機関名称　見出し";
        else if ("hospAdd.name.w2".equals(code)) obj = "医療機関名称";
        else if ("hospAdd.kaisetuname.w1".equals(code)) obj = "医療機関開設者氏名　見出し";
        else if ("hospAdd.kaisetuname.w2".equals(code)) obj = "医療機関開設者氏名";
        else if ("hospAdd.tel.w1".equals(code)) obj = "医療機関電話番号　見出し";
        else if ("hospAdd.tel.w2".equals(code)) obj = "医療機関電話番号";
        else if ("Label3".equals(code)) obj = "医療機関　印";
        else if ("hospCd".equals(code)) obj = "医療機関コード　枠";
        else if ("hospCd.h1.w2".equals(code)) obj = "医療機関コード　見出し";
        else if ("hospCd.h2.w2".equals(code)) obj = "医療機関コード";
        else if ("lblSeikyu".equals(code)) obj = "「下記の通り請求します。」";
        else if ("Label1".equals(code)) obj = "意見書作成料　見出し";
        else if ("ikenSeikyuTotal".equals(code)) obj = "意見書作成料・請求件数 枠";
        else if ("ikenSeikyuTotal.h1.w1".equals(code)) obj = "意見書作成料・請求件数　見出し";
        else if ("ikenSeikyuTotal.h2.w1".equals(code)) obj = "意見書作成料・請求件数";
        else if ("ikenSeikyuTotal.h2.w2".equals(code)) obj = "意見書作成料・請求件数　見出し２";
        else if ("ikenCost".equals(code)) obj = "意見書作成料・請求金額　枠";
        else if ("ikenCost.h1.w1".equals(code)) obj = "意見書作成料・請求金額　見出し";
        else if ("ikenCost.h2.w1".equals(code)) obj = "意見書作成料・請求金額";
        else if ("ikenCost.h2.w2".equals(code)) obj = "意見書作成料・請求金額　見出し２";
        else if ("Label2".equals(code)) obj = "検査料　見出し";
        else if ("kensaSeikyuTotal".equals(code)) obj = "検査料・請求件数　枠";
        else if ("kensaSeikyuTotal.h1.w1".equals(code)) obj = "検査料・請求件数見出し";
        else if ("kensaSeikyuTotal.h2.w1".equals(code)) obj = "検査料・請求件数";
        else if ("kensaSeikyuTotal.h2.w2".equals(code)) obj = "検査料・請求件数　見出し２";
        else if ("kensaCost".equals(code)) obj = "検査料・請求金額　枠";
        else if ("kensaCost.h1.w1".equals(code)) obj = "検査料・請求金額　見出し";
        else if ("kensaCost.h2.w1".equals(code)) obj = "検査料・請求金額";
        else if ("kensaCost.h2.w2".equals(code)) obj = "検査料・請求金額　見出し２";
        else if ("tax".equals(code)) obj = "消費税の総額　枠";
        else if ("tax.h1.w1".equals(code)) obj = "消費税の総額　見出し";
        else if ("tax.h2.w1".equals(code)) obj = "消費税の総額";
        else if ("tax.h2.w2".equals(code)) obj = "消費税の総額　見出し２";
        else if ("seikyuTotal".equals(code)) obj = "合計請求金額　枠";
        else if ("seikyuTotal.h1.w1".equals(code)) obj = "合計請求金額　見出し";
        else if ("seikyuTotal.h2.w1".equals(code)) obj = "合計請求金額";
        else if ("seikyuTotal.h2.w2".equals(code)) obj = "合計請求金額　見出し２";
        else if ("bank".equals(code)) obj = "振込先情報　枠";
        else if ("bank.h1.w1".equals(code)) obj = "振込先金融機関　見出し";
        else if ("bank.h1.w2".equals(code)) obj = "振込先金融機関";
        else if ("bank.h3.w1".equals(code)) obj = "金融機関名　見出し";
        else if ("bank.h3.w2".equals(code)) obj = "金融機関名";
        else if ("bank.h4.w1".equals(code)) obj = "支店名　見出し";
        else if ("bank.h4.w2".equals(code)) obj = "支店名";
        else if ("bank.h5.w1".equals(code)) obj = "口座種類　見出し";
        else if ("bank.h5.w2".equals(code)) obj = "口座種類";
        else if ("bank.h6.w1".equals(code)) obj = "口座番号　見出し";
        else if ("bank.h6.w2".equals(code)) obj = "口座番号";
        else if ("bank.h7.w1".equals(code)) obj = "名義人　見出し";
        else if ("bank.h7.w2".equals(code)) obj = "名義人";
        return obj;
    }

    /**
     * 主治医意見書作成料請求(明細)書の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatMeisaisho(String code, Object obj){
        if ("lblTitle".equals(code)) obj = "タイトル";
        else if ("printData".equals(code)) obj = "印刷日";
        else if ("atena".equals(code)) obj = "請求先保険者名";
        else if ("hiyokensya".equals(code)) obj = "被保険者情報";
        else if ("hiyokensya.h1.w1".equals(code)) obj = "被保険者情報　見出し";
        else if ("hiyokensya.h1.w2".equals(code)) obj = "被保険者情報・被保険者番号　見出し";
        else if ("hiyokensya.h1.w4".equals(code)) obj = "被保険者情報・被保険者番号";
        else if ("hiyokensya.h3.w2".equals(code)) obj = "被保険者情報・ふりがな　見出し";
        else if ("hiyokensya.h3.w4".equals(code)) obj = "被保険者情報・ふりがな";
        else if ("hiyokensya.h4.w2".equals(code)) obj = "被保険者情報・氏名　見出し";
        else if ("hiyokensya.h4.w4".equals(code)) obj = "被保険者情報・氏名";
        else if ("hiyokensya.h5.w2".equals(code)) obj = "被保険者情報・生年月日　見出し1";
        else if ("hiyokensya.h6.w2".equals(code)) obj = "被保険者情報・生年月日　見出し2";
        else if ("hiyokensya.h6.w4".equals(code)) obj = "被保険者情報・生年月日";
        else if ("hiyokensya.h5.w5".equals(code)) obj = "被保険者情報・性別　見出し1";
        else if ("hiyokensya.h6.w5".equals(code)) obj = "被保険者情報・性別　見出し2";
        else if ("hiyokensya.h5.w6".equals(code)) obj = "被保険者情報・性別　「男」";
        else if ("hiyokensya.h6.w6".equals(code)) obj = "被保険者情報・性別　「女」";
        else if ("Label2".equals(code)) obj = "被保険者情報・生年月日・年号　「明治」";
        else if ("Label3".equals(code)) obj = "被保険者情報・生年月日・年号　「大正」";
        else if ("Label4".equals(code)) obj = "被保険者情報・生年月日・年号　「昭和」";
        else if ("meiji".equals(code)) obj = "被保険者情報・生年月日・年号・シェイプ　「明治」";
        else if ("taisyo".equals(code)) obj = "被保険者情報・生年月日・年号・シェイプ　「大正」";
        else if ("syowa".equals(code)) obj = "被保険者情報・生年月日・年号・シェイプ　「昭和」";
        else if ("man".equals(code)) obj = "被保険者情報・性別・シェイプ　「男」";
        else if ("woman".equals(code)) obj = "被保険者情報・性別・シェイプ　「女」";
        else if ("hokensyaNo".equals(code)) obj = "保険者情報";
        else if ("hokensyaNo.h1.w1".equals(code)) obj = "保険者情報・対象年月";
        else if ("hokensyaNo.h2.w1".equals(code)) obj = "保険者情報・保険者番号　見出し";
        else if ("hokensyaNo.h2.w2".equals(code)) obj = "保険者情報・保険者番号";
        else if ("iryoKikan".equals(code)) obj = "医療機関情報";
        else if ("iryoKikan.h1.w1".equals(code)) obj = "医療機関情報　見出し";
        else if ("iryoKikan.h1.w2".equals(code)) obj = "医療機関情報・事業所番号　見出し";
        else if ("iryoKikan.h1.w3".equals(code)) obj = "医療機関情報・事業所番号";
        else if ("iryoKikan.h2.w2".equals(code)) obj = "医療機関情報・事業所名称　見出し";
        else if ("iryoKikan.h2.w3".equals(code)) obj = "医療機関情報・事業所名称";
        else if ("iryoKikan.h4.w2".equals(code)) obj = "医療機関情報・所在地　見出し";
        else if ("iryoKikan.h3.w3".equals(code)) obj = "医療機関情報・所在地・郵便番号　見出し";
        else if ("iryoKikan.h3.w4".equals(code)) obj = "医療機関情報・所在地・郵便番号";
        else if ("iryoKikan.h4.w3".equals(code)) obj = "医療機関情報・所在地";
        else if ("iryoKikan.h5.w3".equals(code)) obj = "医療機関情報・電話番号　見出し";
        else if ("iryoKikan.h5.w5".equals(code)) obj = "医療機関情報・電話番号";
        else if ("dateList".equals(code)) obj = "日付情報";
        else if ("dateList.h1.w1".equals(code)) obj = "日付情報・作成依頼日　見出し";
        else if ("dateList.h1.w2".equals(code)) obj = "日付情報・作成依頼日";
        else if ("dateList.h1.w3".equals(code)) obj = "日付情報・依頼番号　見出し";
        else if ("dateList.h1.w4".equals(code)) obj = "日付情報・依頼番号";
        else if ("dateList.h2.w1".equals(code)) obj = "日付情報・意見書作成日　見出し";
        else if ("dateList.h2.w2".equals(code)) obj = "日付情報・意見書作成日";
        else if ("dateList.h2.w3".equals(code)) obj = "日付情報・意見書送付日　見出し";
        else if ("dateList.h2.w4".equals(code)) obj = "日付情報・意見書送付日";
        else if ("dateList.h1.w5".equals(code)) obj = "日付情報・保険者確認　見出し1";
        else if ("dateList.h1.w6".equals(code)) obj = "日付情報・保険者確認　見出し2";
        else if ("dateList.h1.w7".equals(code)) obj = "日付情報・保険者確認　「※」";
        else if ("Grid1".equals(code)) obj = "意見書作成料情報";
        else if ("Grid1.h1.w1".equals(code)) obj = "意見書作成料情報　見出し";
        else if ("Grid1.h1.w2".equals(code)) obj = "意見書作成料情報・種別　見出し";
        else if ("Grid1.h1.w3".equals(code)) obj = "意見書作成料情報・種別1";
        else if ("Grid1.h1.w4".equals(code)) obj = "意見書作成料情報・種別2";
        else if ("Grid1.h1.w5".equals(code)) obj = "意見書作成料情報・金額　見出し";
        else if ("Grid1.h1.w6".equals(code)) obj = "意見書作成料情報・金額";
        else if ("Grid1.h1.w7".equals(code)) obj = "意見書作成料情報・金額　単位";
        else if ("costList1".equals(code)) obj = "診察・検査費用情報";
        else if ("costList1.h1.w1".equals(code)) obj = "診察・検査費用情報　見出し";
        else if ("costList1.h1.w2".equals(code)) obj = "診察・検査費用情報・内訳　見出し";
        else if ("costList1.h1.point".equals(code)) obj = "診察・検査費用情報・点数　見出し";
        else if ("costList1.h1.tekiyo".equals(code)) obj = "診察・検査費用情報・摘要　見出し";
        else if ("costList1.shosin.w3".equals(code)) obj = "診察・検査費用情報・初診　見出し";
        else if ("costList1.shosin.point".equals(code)) obj = "診察・検査費用情報・初診　点数";
        else if ("costList1.shosin.tekiyo".equals(code)) obj = "診察・検査費用情報・初診　摘要";
        else if ("costList1.xray.w2".equals(code)) obj = "診察・検査費用情報・検査　見出し";
        else if ("costList1.xray.w4".equals(code)) obj = "診察・検査費用情報・胸部単純Ｘ線撮影　見出し";
        else if ("costList1.xray.point".equals(code)) obj = "診察・検査費用情報・胸部単純Ｘ線撮影　点数";
        else if ("costList1.xray.tekiyo".equals(code)) obj = "診察・検査費用情報・胸部単純Ｘ線撮影　摘要";
        else if ("costList1.bld_ippan.w4".equals(code)) obj = "診察・検査費用情報・血液一般検査　見出し";
        else if ("costList1.bld_ippan.point".equals(code)) obj = "診察・検査費用情報・血液一般検査　点数";
        else if ("costList1.bld_ippan.tekiyo".equals(code)) obj = "診察・検査費用情報・血液一般検査　摘要";
        else if ("costList1.bld_kagaku.w4".equals(code)) obj = "診察・検査費用情報・血液化学検査　見出し";
        else if ("costList1.bld_kagaku.point".equals(code)) obj = "診察・検査費用情報・血液化学検査　点数";
        else if ("costList1.bld_kagaku.tekiyo".equals(code)) obj = "診察・検査費用情報・血液化学検査　摘要";
        else if ("costList1.nyo.w4".equals(code)) obj = "診察・検査費用情報・尿中一般物質定性半定量検査　見出し";
        else if ("costList1.nyo.point".equals(code)) obj = "診察・検査費用情報・尿中一般物質定性半定量検査　点数";
        else if ("costList1.nyo.tekiyo".equals(code)) obj = "診察・検査費用情報・尿中一般物質定性半定量検査　摘要";
        else if ("costList1.total.w3".equals(code)) obj = "診察・検査費用情報・合計　見出し";
        else if ("costList1.total.point".equals(code)) obj = "診察・検査費用情報・合計　点数";
        else if ("costList1.total.tekiyo".equals(code)) obj = "診察・検査費用情報・点数合計×10円　見出し";
        else if ("costList1.total.w7".equals(code)) obj = "診察・検査費用情報・点数合計×10円　金額";
        else if ("costList1.total.w8".equals(code)) obj = "診察・検査費用情報・点数合計×10円　単位";
        else if ("bank".equals(code)) obj = "振込先金融機関情報";
        else if ("bank.h1.w1".equals(code)) obj = "振込先金融機関情報　見出し";
        else if ("bank.h3.w1".equals(code)) obj = "振込先金融機関情報・金融機関名　見出し";
        else if ("bank.h3.w2".equals(code)) obj = "振込先金融機関情報・金融機関名";
        else if ("bank.h4.w1".equals(code)) obj = "振込先金融機関情報・支店名　見出し";
        else if ("bank.h4.w2".equals(code)) obj = "振込先金融機関情報・支店名";
        else if ("bank.h5.w1".equals(code)) obj = "振込先金融機関情報・口座種類　見出し";
        else if ("bank.h5.w2".equals(code)) obj = "振込先金融機関情報・口座種類";
        else if ("bank.h6.w1".equals(code)) obj = "振込先金融機関情報・口座番号　見出し";
        else if ("bank.h6.w2".equals(code)) obj = "振込先金融機関情報・口座番号";
        else if ("bank.h7.w1".equals(code)) obj = "振込先金融機関情報・名義人　見出し";
        else if ("bank.h7.w2".equals(code)) obj = "振込先金融機関情報・名義人";
        else if ("costList2".equals(code)) obj = "請求額情報";
        else if ("costList2.h1.w1".equals(code)) obj = "請求額情報　見出し";
        else if ("costList2.h1.w2".equals(code)) obj = "請求額情報・意見書作成料　見出し";
        else if ("costList2.h1.cost".equals(code)) obj = "請求額情報・意見書作成料";
        else if ("costList2.h1.w4".equals(code)) obj = "請求額情報・意見書作成料　単位";
        else if ("costList2.h2.w2".equals(code)) obj = "請求額情報・診察・検査費用　見出し";
        else if ("costList2.h2.cost".equals(code)) obj = "請求額情報・診察・検査費用";
        else if ("costList2.h2.w4".equals(code)) obj = "請求額情報・診察・検査費用　単位";
        else if ("costList2.h3.w2".equals(code)) obj = "請求額情報・消費税　見出し";
        else if ("costList2.h3.cost".equals(code)) obj = "請求額情報・消費税";
        else if ("costList2.h3.w4".equals(code)) obj = "請求額情報・消費税　単位";
        else if ("costList2.h4.w2".equals(code)) obj = "請求額情報・合計　見出し";
        else if ("costList2.h4.cost".equals(code)) obj = "請求額情報・合計";
        else if ("costList2.h4.w4".equals(code)) obj = "請求額情報・合計　単位";
        else if ("CostTotal".equals(code)) obj = "支払決定額情報";
        else if ("CostTotal.h1.w1".equals(code)) obj = "支払決定額情報・支払決定額　見出し";
        else if ("CostTotal.h1.w2".equals(code)) obj = "支払決定額情報・支払決定額";
        else if ("CostTotal.h1.w3".equals(code)) obj = "支払決定額情報・支払決定額　単位";
        else if ("Label1".equals(code)) obj = "「※の欄には記入しないで下さい」";
        return obj;
    }

    /**
     * 登録患者一覧の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatPatientList(String code, Object obj){
        if ("date".equals(code)) obj = "作成日";
        else if ("page".equals(code)) obj = "ページ数";
        else if ("table".equals(code)) obj = "登録患者一覧　枠";
        else if ("table.h1.w1".equals(code)) obj = "列名「患者ID」";
        else if ("table.h1.w2".equals(code)) obj = "列名「氏名」";
        else if ("table.h1.w3".equals(code)) obj = "列名「ふりがな」";
        else if ("table.h1.w4".equals(code)) obj = "列名「性別」";
        else if ("table.h1.w5".equals(code)) obj = "列名「年齢」";
        else if ("table.h1.w6".equals(code)) obj = "列名「生年月日」";
        else if ("table.h1.w7".equals(code)) obj = "列名「最終診察日」";
        else if ("table.h1.w8".equals(code)) obj = "列名「最新意見書記入日」";
        else if ("table.h1.w9".equals(code)) obj = "列名「最新指示書記入日」";
        else if ("table.h2.w1".equals(code)) obj = "1行目「患者ＩＤ」";
        else if ("table.h2.w2".equals(code)) obj = "1行目「氏名」";
        else if ("table.h2.w3".equals(code)) obj = "1行目「ふりがな」";
        else if ("table.h2.w4".equals(code)) obj = "1行目「性別」";
        else if ("table.h2.w5".equals(code)) obj = "1行目「年齢」";
        else if ("table.h2.w6".equals(code)) obj = "1行目「生年月日」";
        else if ("table.h2.w7".equals(code)) obj = "1行目「最終診察日」";
        else if ("table.h2.w8".equals(code)) obj = "1行目「最新意見書記入日」";
        else if ("table.h2.w9".equals(code)) obj = "1行目「最新指示書記入日」";
        else if ("table.h3.w1".equals(code)) obj = "2行目「患者ＩＤ」";
        else if ("table.h3.w2".equals(code)) obj = "2行目「氏名」";
        else if ("table.h3.w3".equals(code)) obj = "2行目「ふりがな」";
        else if ("table.h3.w4".equals(code)) obj = "2行目「性別」";
        else if ("table.h3.w5".equals(code)) obj = "2行目「年齢」";
        else if ("table.h3.w6".equals(code)) obj = "2行目「生年月日」";
        else if ("table.h3.w7".equals(code)) obj = "2行目「最終診察日」";
        else if ("table.h3.w8".equals(code)) obj = "2行目「最新意見書記入日」";
        else if ("table.h3.w9".equals(code)) obj = "2行目「最新指示書記入日」";
        else if ("table.h4.w1".equals(code)) obj = "3行目「患者ＩＤ」";
        else if ("table.h4.w2".equals(code)) obj = "3行目「氏名」";
        else if ("table.h4.w3".equals(code)) obj = "3行目「ふりがな」";
        else if ("table.h4.w4".equals(code)) obj = "3行目「性別」";
        else if ("table.h4.w5".equals(code)) obj = "3行目「年齢」";
        else if ("table.h4.w6".equals(code)) obj = "3行目「生年月日」";
        else if ("table.h4.w7".equals(code)) obj = "3行目「最終診察日」";
        else if ("table.h4.w8".equals(code)) obj = "3行目「最新意見書記入日」";
        else if ("table.h4.w9".equals(code)) obj = "3行目「最新指示書記入日」";
        else if ("table.h5.w1".equals(code)) obj = "4行目「患者ＩＤ」";
        else if ("table.h5.w2".equals(code)) obj = "4行目「氏名」";
        else if ("table.h5.w3".equals(code)) obj = "4行目「ふりがな」";
        else if ("table.h5.w4".equals(code)) obj = "4行目「性別」";
        else if ("table.h5.w5".equals(code)) obj = "4行目「年齢」";
        else if ("table.h5.w6".equals(code)) obj = "4行目「生年月日」";
        else if ("table.h5.w7".equals(code)) obj = "4行目「最終診察日」";
        else if ("table.h5.w8".equals(code)) obj = "4行目「最新意見書記入日」";
        else if ("table.h5.w9".equals(code)) obj = "4行目「最新指示書記入日」";
        else if ("table.h6.w1".equals(code)) obj = "5行目「患者ＩＤ」";
        else if ("table.h6.w2".equals(code)) obj = "5行目「氏名」";
        else if ("table.h6.w3".equals(code)) obj = "5行目「ふりがな」";
        else if ("table.h6.w4".equals(code)) obj = "5行目「性別」";
        else if ("table.h6.w5".equals(code)) obj = "5行目「年齢」";
        else if ("table.h6.w6".equals(code)) obj = "5行目「生年月日」";
        else if ("table.h6.w7".equals(code)) obj = "5行目「最終診察日」";
        else if ("table.h6.w8".equals(code)) obj = "5行目「最新意見書記入日」";
        else if ("table.h6.w9".equals(code)) obj = "5行目「最新指示書記入日」";
        else if ("table.h7.w1".equals(code)) obj = "6行目「患者ＩＤ」";
        else if ("table.h7.w2".equals(code)) obj = "6行目「氏名」";
        else if ("table.h7.w3".equals(code)) obj = "6行目「ふりがな」";
        else if ("table.h7.w4".equals(code)) obj = "6行目「性別」";
        else if ("table.h7.w5".equals(code)) obj = "6行目「年齢」";
        else if ("table.h7.w6".equals(code)) obj = "6行目「生年月日」";
        else if ("table.h7.w7".equals(code)) obj = "6行目「最終診察日」";
        else if ("table.h7.w8".equals(code)) obj = "6行目「最新意見書記入日」";
        else if ("table.h7.w9".equals(code)) obj = "6行目「最新指示書記入日」";
        else if ("table.h8.w1".equals(code)) obj = "7行目「患者ＩＤ」";
        else if ("table.h8.w2".equals(code)) obj = "7行目「氏名」";
        else if ("table.h8.w3".equals(code)) obj = "7行目「ふりがな」";
        else if ("table.h8.w4".equals(code)) obj = "7行目「性別」";
        else if ("table.h8.w5".equals(code)) obj = "7行目「年齢」";
        else if ("table.h8.w6".equals(code)) obj = "7行目「生年月日」";
        else if ("table.h8.w7".equals(code)) obj = "7行目「最終診察日」";
        else if ("table.h8.w8".equals(code)) obj = "7行目「最新意見書記入日」";
        else if ("table.h8.w9".equals(code)) obj = "7行目「最新指示書記入日」";
        else if ("table.h9.w1".equals(code)) obj = "8行目「患者ＩＤ」";
        else if ("table.h9.w2".equals(code)) obj = "8行目「氏名」";
        else if ("table.h9.w3".equals(code)) obj = "8行目「ふりがな」";
        else if ("table.h9.w4".equals(code)) obj = "8行目「性別」";
        else if ("table.h9.w5".equals(code)) obj = "8行目「年齢」";
        else if ("table.h9.w6".equals(code)) obj = "8行目「生年月日」";
        else if ("table.h9.w7".equals(code)) obj = "8行目「最終診察日」";
        else if ("table.h9.w8".equals(code)) obj = "8行目「最新意見書記入日」";
        else if ("table.h9.w9".equals(code)) obj = "8行目「最新指示書記入日」";
        else if ("table.h10.w1".equals(code)) obj = "9行目「患者ＩＤ」";
        else if ("table.h10.w2".equals(code)) obj = "9行目「氏名」";
        else if ("table.h10.w3".equals(code)) obj = "9行目「ふりがな」";
        else if ("table.h10.w4".equals(code)) obj = "9行目「性別」";
        else if ("table.h10.w5".equals(code)) obj = "9行目「年齢」";
        else if ("table.h10.w6".equals(code)) obj = "9行目「生年月日」";
        else if ("table.h10.w7".equals(code)) obj = "9行目「最終診察日」";
        else if ("table.h10.w8".equals(code)) obj = "9行目「最新意見書記入日」";
        else if ("table.h10.w9".equals(code)) obj = "9行目「最新指示書記入日」";
        else if ("table.h11.w1".equals(code)) obj = "10行目「患者ＩＤ」";
        else if ("table.h11.w2".equals(code)) obj = "10行目「氏名」";
        else if ("table.h11.w3".equals(code)) obj = "10行目「ふりがな」";
        else if ("table.h11.w4".equals(code)) obj = "10行目「性別」";
        else if ("table.h11.w5".equals(code)) obj = "10行目「年齢」";
        else if ("table.h11.w6".equals(code)) obj = "10行目「生年月日」";
        else if ("table.h11.w7".equals(code)) obj = "10行目「最終診察日」";
        else if ("table.h11.w8".equals(code)) obj = "10行目「最新意見書記入日」";
        else if ("table.h11.w9".equals(code)) obj = "10行目「最新指示書記入日」";
        else if ("table.h12.w1".equals(code)) obj = "11行目「患者ＩＤ」";
        else if ("table.h12.w2".equals(code)) obj = "11行目「氏名」";
        else if ("table.h12.w3".equals(code)) obj = "11行目「ふりがな」";
        else if ("table.h12.w4".equals(code)) obj = "11行目「性別」";
        else if ("table.h12.w5".equals(code)) obj = "11行目「年齢」";
        else if ("table.h12.w6".equals(code)) obj = "11行目「生年月日」";
        else if ("table.h12.w7".equals(code)) obj = "11行目「最終診察日」";
        else if ("table.h12.w8".equals(code)) obj = "11行目「最新意見書記入日」";
        else if ("table.h12.w9".equals(code)) obj = "11行目「最新指示書記入日」";
        else if ("table.h13.w1".equals(code)) obj = "12行目「患者ＩＤ」";
        else if ("table.h13.w2".equals(code)) obj = "12行目「氏名」";
        else if ("table.h13.w3".equals(code)) obj = "12行目「ふりがな」";
        else if ("table.h13.w4".equals(code)) obj = "12行目「性別」";
        else if ("table.h13.w5".equals(code)) obj = "12行目「年齢」";
        else if ("table.h13.w6".equals(code)) obj = "12行目「生年月日」";
        else if ("table.h13.w7".equals(code)) obj = "12行目「最終診察日」";
        else if ("table.h13.w8".equals(code)) obj = "12行目「最新意見書記入日」";
        else if ("table.h13.w9".equals(code)) obj = "12行目「最新指示書記入日」";
        else if ("table.h14.w1".equals(code)) obj = "13行目「患者ＩＤ」";
        else if ("table.h14.w2".equals(code)) obj = "13行目「氏名」";
        else if ("table.h14.w3".equals(code)) obj = "13行目「ふりがな」";
        else if ("table.h14.w4".equals(code)) obj = "13行目「性別」";
        else if ("table.h14.w5".equals(code)) obj = "13行目「年齢」";
        else if ("table.h14.w6".equals(code)) obj = "13行目「生年月日」";
        else if ("table.h14.w7".equals(code)) obj = "13行目「最終診察日」";
        else if ("table.h14.w8".equals(code)) obj = "13行目「最新意見書記入日」";
        else if ("table.h14.w9".equals(code)) obj = "13行目「最新指示書記入日」";
        else if ("table.h15.w1".equals(code)) obj = "14行目「患者ＩＤ」";
        else if ("table.h15.w2".equals(code)) obj = "14行目「氏名」";
        else if ("table.h15.w3".equals(code)) obj = "14行目「ふりがな」";
        else if ("table.h15.w4".equals(code)) obj = "14行目「性別」";
        else if ("table.h15.w5".equals(code)) obj = "14行目「年齢」";
        else if ("table.h15.w6".equals(code)) obj = "14行目「生年月日」";
        else if ("table.h15.w7".equals(code)) obj = "14行目「最終診察日」";
        else if ("table.h15.w8".equals(code)) obj = "14行目「最新意見書記入日」";
        else if ("table.h15.w9".equals(code)) obj = "14行目「最新指示書記入日」";
        else if ("table.h16.w1".equals(code)) obj = "15行目「患者ＩＤ」";
        else if ("table.h16.w2".equals(code)) obj = "15行目「氏名」";
        else if ("table.h16.w3".equals(code)) obj = "15行目「ふりがな」";
        else if ("table.h16.w4".equals(code)) obj = "15行目「性別」";
        else if ("table.h16.w5".equals(code)) obj = "15行目「年齢」";
        else if ("table.h16.w6".equals(code)) obj = "15行目「生年月日」";
        else if ("table.h16.w7".equals(code)) obj = "15行目「最終診察日」";
        else if ("table.h16.w8".equals(code)) obj = "15行目「最新意見書記入日」";
        else if ("table.h16.w9".equals(code)) obj = "15行目「最新指示書記入日」";
        else if ("table.h17.w1".equals(code)) obj = "16行目「患者ＩＤ」";
        else if ("table.h17.w2".equals(code)) obj = "16行目「氏名」";
        else if ("table.h17.w3".equals(code)) obj = "16行目「ふりがな」";
        else if ("table.h17.w4".equals(code)) obj = "16行目「性別」";
        else if ("table.h17.w5".equals(code)) obj = "16行目「年齢」";
        else if ("table.h17.w6".equals(code)) obj = "16行目「生年月日」";
        else if ("table.h17.w7".equals(code)) obj = "16行目「最終診察日」";
        else if ("table.h17.w8".equals(code)) obj = "16行目「最新意見書記入日」";
        else if ("table.h17.w9".equals(code)) obj = "16行目「最新指示書記入日」";
        else if ("table.h18.w1".equals(code)) obj = "17行目「患者ＩＤ」";
        else if ("table.h18.w2".equals(code)) obj = "17行目「氏名」";
        else if ("table.h18.w3".equals(code)) obj = "17行目「ふりがな」";
        else if ("table.h18.w4".equals(code)) obj = "17行目「性別」";
        else if ("table.h18.w5".equals(code)) obj = "17行目「年齢」";
        else if ("table.h18.w6".equals(code)) obj = "17行目「生年月日」";
        else if ("table.h18.w7".equals(code)) obj = "17行目「最終診察日」";
        else if ("table.h18.w8".equals(code)) obj = "17行目「最新意見書記入日」";
        else if ("table.h18.w9".equals(code)) obj = "17行目「最新指示書記入日」";
        else if ("table.h19.w1".equals(code)) obj = "18行目「患者ＩＤ」";
        else if ("table.h19.w2".equals(code)) obj = "18行目「氏名」";
        else if ("table.h19.w3".equals(code)) obj = "18行目「ふりがな」";
        else if ("table.h19.w4".equals(code)) obj = "18行目「性別」";
        else if ("table.h19.w5".equals(code)) obj = "18行目「年齢」";
        else if ("table.h19.w6".equals(code)) obj = "18行目「生年月日」";
        else if ("table.h19.w7".equals(code)) obj = "18行目「最終診察日」";
        else if ("table.h19.w8".equals(code)) obj = "18行目「最新意見書記入日」";
        else if ("table.h19.w9".equals(code)) obj = "18行目「最新指示書記入日」";
        else if ("table.h20.w1".equals(code)) obj = "19行目「患者ＩＤ」";
        else if ("table.h20.w2".equals(code)) obj = "19行目「氏名」";
        else if ("table.h20.w3".equals(code)) obj = "19行目「ふりがな」";
        else if ("table.h20.w4".equals(code)) obj = "19行目「性別」";
        else if ("table.h20.w5".equals(code)) obj = "19行目「年齢」";
        else if ("table.h20.w6".equals(code)) obj = "19行目「生年月日」";
        else if ("table.h20.w7".equals(code)) obj = "19行目「最終診察日」";
        else if ("table.h20.w8".equals(code)) obj = "19行目「最新意見書記入日」";
        else if ("table.h20.w9".equals(code)) obj = "19行目「最新指示書記入日」";
        else if ("table.h21.w1".equals(code)) obj = "20行目「患者ＩＤ」";
        else if ("table.h21.w2".equals(code)) obj = "20行目「氏名」";
        else if ("table.h21.w3".equals(code)) obj = "20行目「ふりがな」";
        else if ("table.h21.w4".equals(code)) obj = "20行目「性別」";
        else if ("table.h21.w5".equals(code)) obj = "20行目「年齢」";
        else if ("table.h21.w6".equals(code)) obj = "20行目「生年月日」";
        else if ("table.h21.w7".equals(code)) obj = "20行目「最終診察日」";
        else if ("table.h21.w8".equals(code)) obj = "20行目「最新意見書記入日」";
        else if ("table.h21.w9".equals(code)) obj = "20行目「最新指示書記入日」";
        else if ("title".equals(code)) obj = "登録患者一覧・見出し　枠";
        else if ("title.h1.w1".equals(code)) obj = "「登録患者一覧」　見出し";
        // 2006/08/25
        // 医師意見書対応カラム追加
        else if ("table.h1.w10".equals(code)) obj = "列名「最新医師意見書記入日」";
        else if ("table.h2.w10".equals(code)) obj = "1行目「最新医師意見書記入日」";
        else if ("table.h3.w10".equals(code)) obj = "2行目「最新医師意見書記入日」";
        else if ("table.h4.w10".equals(code)) obj = "3行目「最新医師意見書記入日」";
        else if ("table.h5.w10".equals(code)) obj = "4行目「最新医師意見書記入日」";
        else if ("table.h6.w10".equals(code)) obj = "5行目「最新医師意見書記入日」";
        else if ("table.h7.w10".equals(code)) obj = "6行目「最新医師意見書記入日」";
        else if ("table.h8.w10".equals(code)) obj = "7行目「最新医師意見書記入日」";
        else if ("table.h9.w10".equals(code)) obj = "8行目「最新医師意見書記入日」";
        else if ("table.h10.w10".equals(code)) obj = "9行目「最新医師意見書記入日」";
        else if ("table.h11.w10".equals(code)) obj = "10行目「最新医師意見書記入日」";
        else if ("table.h12.w10".equals(code)) obj = "11行目「最新医師意見書記入日」";
        else if ("table.h13.w10".equals(code)) obj = "12行目「最新医師意見書記入日」";
        else if ("table.h14.w10".equals(code)) obj = "13行目「最新医師意見書記入日」";
        else if ("table.h15.w10".equals(code)) obj = "14行目「最新医師意見書記入日」";
        else if ("table.h16.w10".equals(code)) obj = "15行目「最新医師意見書記入日」";
        else if ("table.h17.w10".equals(code)) obj = "16行目「最新医師意見書記入日」";
        else if ("table.h18.w10".equals(code)) obj = "17行目「最新医師意見書記入日」";
        else if ("table.h19.w10".equals(code)) obj = "18行目「最新医師意見書記入日」";
        else if ("table.h20.w10".equals(code)) obj = "19行目「最新医師意見書記入日」";
        else if ("table.h21.w10".equals(code)) obj = "20行目「最新医師意見書記入日」";
        return obj;
    }

    /**
     * 請求対象意見書一覧の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatSeikyuIkenshoIchiran(String code, Object obj){
        if ("lblTitle".equals(code)) obj = "「請求対象意見書一覧」";
        else if ("Shape1".equals(code)) obj = "「請求対象意見書一覧」　下線";
        else if ("hokensya".equals(code)) obj = "保険者情報";
        else if ("date".equals(code)) obj = "印刷日";
        else if ("page".equals(code)) obj = "ページ番号";
        else if ("dateRange".equals(code)) obj = "記入日情報";
        else if ("dateRange.data.title".equals(code)) obj = "「記入日：」";
        else if ("dateRange.data.fromY".equals(code)) obj = "記入日・開始日・年";
        else if ("dateRange.data.w3".equals(code)) obj = "「年」　1";
        else if ("dateRange.data.fromM".equals(code)) obj = "記入日・開始日・月";
        else if ("dateRange.data.w5".equals(code)) obj = "「月」　1";
        else if ("dateRange.data.fromD".equals(code)) obj = "記入日・開始日・日";
        else if ("dateRange.data.w7".equals(code)) obj = "「日」　1";
        else if ("dateRange.data.w9".equals(code)) obj = "「から」";
        else if ("dateRange.data.toY".equals(code)) obj = "記入日・終了日・年";
        else if ("dateRange.data.w12".equals(code)) obj = "「年」　2";
        else if ("dateRange.data.toM".equals(code)) obj = "記入日・終了日・月";
        else if ("dateRange.data.w14".equals(code)) obj = "「月」　2";
        else if ("dateRange.data.toD".equals(code)) obj = "記入日・終了日・日";
        else if ("dateRange.data.w16".equals(code)) obj = "「日」　2";
        else if ("table".equals(code)) obj = "意見書一覧";
        else if ("table.title.HAKKOU_KBN".equals(code)) obj = "列名　「請求書」";
        else if ("table.title.PATIENT_NM".equals(code)) obj = "列名　「氏名」";
        else if ("table.title.PATIENT_KN".equals(code)) obj = "列名　「ふりがな」";
        else if ("table.title.SEX".equals(code)) obj = "列名　「性別」";
        else if ("table.title.AGE".equals(code)) obj = "列名　「年齢」";
        else if ("table.title.BIRTHDAY".equals(code)) obj = "列名　「生年月日」";
        else if ("table.title.INSURED_NO".equals(code)) obj = "列名　「被保険者番号」";
        else if ("table.title.DR_NM".equals(code)) obj = "列名　「医師名」";
        else if ("table.title.REQ_DT".equals(code)) obj = "列名　「作成依頼日」";
        else if ("table.title.KINYU_DT".equals(code)) obj = "列名　「意見書記入日」";
        else if ("table.title.SEND_DT".equals(code)) obj = "列名　「意見書送付日」";
        else if ("table.h1.HAKKOU_KBN".equals(code)) obj = "1行目　「請求書」";
        else if ("table.h1.PATIENT_NM".equals(code)) obj = "1行目　「氏名」";
        else if ("table.h1.PATIENT_KN".equals(code)) obj = "1行目　「ふりがな」";
        else if ("table.h1.SEX".equals(code)) obj = "1行目　「性別」";
        else if ("table.h1.AGE".equals(code)) obj = "1行目　「年齢」";
        else if ("table.h1.BIRTHDAY".equals(code)) obj = "1行目　「生年月日」";
        else if ("table.h1.INSURED_NO".equals(code)) obj = "1行目　「被保険者番号」";
        else if ("table.h1.DR_NM".equals(code)) obj = "1行目　「医師名」";
        else if ("table.h1.REQ_DT".equals(code)) obj = "1行目　「作成依頼日」";
        else if ("table.h1.KINYU_DT".equals(code)) obj = "1行目　「意見書記入日」";
        else if ("table.h1.SEND_DT".equals(code)) obj = "1行目　「意見書送付日」";
        else if ("table.h2.HAKKOU_KBN".equals(code)) obj = "2行目　「請求書」";
        else if ("table.h2.PATIENT_NM".equals(code)) obj = "2行目　「氏名」";
        else if ("table.h2.PATIENT_KN".equals(code)) obj = "2行目　「ふりがな」";
        else if ("table.h2.SEX".equals(code)) obj = "2行目　「性別」";
        else if ("table.h2.AGE".equals(code)) obj = "2行目　「年齢」";
        else if ("table.h2.BIRTHDAY".equals(code)) obj = "2行目　「生年月日」";
        else if ("table.h2.INSURED_NO".equals(code)) obj = "2行目　「被保険者番号」";
        else if ("table.h2.DR_NM".equals(code)) obj = "2行目　「医師名」";
        else if ("table.h2.REQ_DT".equals(code)) obj = "2行目　「作成依頼日」";
        else if ("table.h2.KINYU_DT".equals(code)) obj = "2行目　「意見書記入日」";
        else if ("table.h2.SEND_DT".equals(code)) obj = "2行目　「意見書送付日」";
        else if ("table.h3.HAKKOU_KBN".equals(code)) obj = "3行目　「請求書」";
        else if ("table.h3.PATIENT_NM".equals(code)) obj = "3行目　「氏名」";
        else if ("table.h3.PATIENT_KN".equals(code)) obj = "3行目　「ふりがな」";
        else if ("table.h3.SEX".equals(code)) obj = "3行目　「性別」";
        else if ("table.h3.AGE".equals(code)) obj = "3行目　「年齢」";
        else if ("table.h3.BIRTHDAY".equals(code)) obj = "3行目　「生年月日」";
        else if ("table.h3.INSURED_NO".equals(code)) obj = "3行目　「被保険者番号」";
        else if ("table.h3.DR_NM".equals(code)) obj = "3行目　「医師名」";
        else if ("table.h3.REQ_DT".equals(code)) obj = "3行目　「作成依頼日」";
        else if ("table.h3.KINYU_DT".equals(code)) obj = "3行目　「意見書記入日」";
        else if ("table.h3.SEND_DT".equals(code)) obj = "3行目　「意見書送付日」";
        else if ("table.h4.HAKKOU_KBN".equals(code)) obj = "4行目　「請求書」";
        else if ("table.h4.PATIENT_NM".equals(code)) obj = "4行目　「氏名」";
        else if ("table.h4.PATIENT_KN".equals(code)) obj = "4行目　「ふりがな」";
        else if ("table.h4.SEX".equals(code)) obj = "4行目　「性別」";
        else if ("table.h4.AGE".equals(code)) obj = "4行目　「年齢」";
        else if ("table.h4.BIRTHDAY".equals(code)) obj = "4行目　「生年月日」";
        else if ("table.h4.INSURED_NO".equals(code)) obj = "4行目　「被保険者番号」";
        else if ("table.h4.DR_NM".equals(code)) obj = "4行目　「医師名」";
        else if ("table.h4.REQ_DT".equals(code)) obj = "4行目　「作成依頼日」";
        else if ("table.h4.KINYU_DT".equals(code)) obj = "4行目　「意見書記入日」";
        else if ("table.h4.SEND_DT".equals(code)) obj = "4行目　「意見書送付日」";
        else if ("table.h5.HAKKOU_KBN".equals(code)) obj = "5行目　「請求書」";
        else if ("table.h5.PATIENT_NM".equals(code)) obj = "5行目　「氏名」";
        else if ("table.h5.PATIENT_KN".equals(code)) obj = "5行目　「ふりがな」";
        else if ("table.h5.SEX".equals(code)) obj = "5行目　「性別」";
        else if ("table.h5.AGE".equals(code)) obj = "5行目　「年齢」";
        else if ("table.h5.BIRTHDAY".equals(code)) obj = "5行目　「生年月日」";
        else if ("table.h5.INSURED_NO".equals(code)) obj = "5行目　「被保険者番号」";
        else if ("table.h5.DR_NM".equals(code)) obj = "5行目　「医師名」";
        else if ("table.h5.REQ_DT".equals(code)) obj = "5行目　「作成依頼日」";
        else if ("table.h5.KINYU_DT".equals(code)) obj = "5行目　「意見書記入日」";
        else if ("table.h5.SEND_DT".equals(code)) obj = "5行目　「意見書送付日」";
        else if ("table.h6.HAKKOU_KBN".equals(code)) obj = "6行目　「請求書」";
        else if ("table.h6.PATIENT_NM".equals(code)) obj = "6行目　「氏名」";
        else if ("table.h6.PATIENT_KN".equals(code)) obj = "6行目　「ふりがな」";
        else if ("table.h6.SEX".equals(code)) obj = "6行目　「性別」";
        else if ("table.h6.AGE".equals(code)) obj = "6行目　「年齢」";
        else if ("table.h6.BIRTHDAY".equals(code)) obj = "6行目　「生年月日」";
        else if ("table.h6.INSURED_NO".equals(code)) obj = "6行目　「被保険者番号」";
        else if ("table.h6.DR_NM".equals(code)) obj = "6行目　「医師名」";
        else if ("table.h6.REQ_DT".equals(code)) obj = "6行目　「作成依頼日」";
        else if ("table.h6.KINYU_DT".equals(code)) obj = "6行目　「意見書記入日」";
        else if ("table.h6.SEND_DT".equals(code)) obj = "6行目　「意見書送付日」";
        else if ("table.h7.HAKKOU_KBN".equals(code)) obj = "7行目　「請求書」";
        else if ("table.h7.PATIENT_NM".equals(code)) obj = "7行目　「氏名」";
        else if ("table.h7.PATIENT_KN".equals(code)) obj = "7行目　「ふりがな」";
        else if ("table.h7.SEX".equals(code)) obj = "7行目　「性別」";
        else if ("table.h7.AGE".equals(code)) obj = "7行目　「年齢」";
        else if ("table.h7.BIRTHDAY".equals(code)) obj = "7行目　「生年月日」";
        else if ("table.h7.INSURED_NO".equals(code)) obj = "7行目　「被保険者番号」";
        else if ("table.h7.DR_NM".equals(code)) obj = "7行目　「医師名」";
        else if ("table.h7.REQ_DT".equals(code)) obj = "7行目　「作成依頼日」";
        else if ("table.h7.KINYU_DT".equals(code)) obj = "7行目　「意見書記入日」";
        else if ("table.h7.SEND_DT".equals(code)) obj = "7行目　「意見書送付日」";
        else if ("table.h8.HAKKOU_KBN".equals(code)) obj = "8行目　「請求書」";
        else if ("table.h8.PATIENT_NM".equals(code)) obj = "8行目　「氏名」";
        else if ("table.h8.PATIENT_KN".equals(code)) obj = "8行目　「ふりがな」";
        else if ("table.h8.SEX".equals(code)) obj = "8行目　「性別」";
        else if ("table.h8.AGE".equals(code)) obj = "8行目　「年齢」";
        else if ("table.h8.BIRTHDAY".equals(code)) obj = "8行目　「生年月日」";
        else if ("table.h8.INSURED_NO".equals(code)) obj = "8行目　「被保険者番号」";
        else if ("table.h8.DR_NM".equals(code)) obj = "8行目　「医師名」";
        else if ("table.h8.REQ_DT".equals(code)) obj = "8行目　「作成依頼日」";
        else if ("table.h8.KINYU_DT".equals(code)) obj = "8行目　「意見書記入日」";
        else if ("table.h8.SEND_DT".equals(code)) obj = "8行目　「意見書送付日」";
        else if ("table.h9.HAKKOU_KBN".equals(code)) obj = "9行目　「請求書」";
        else if ("table.h9.PATIENT_NM".equals(code)) obj = "9行目　「氏名」";
        else if ("table.h9.PATIENT_KN".equals(code)) obj = "9行目　「ふりがな」";
        else if ("table.h9.SEX".equals(code)) obj = "9行目　「性別」";
        else if ("table.h9.AGE".equals(code)) obj = "9行目　「年齢」";
        else if ("table.h9.BIRTHDAY".equals(code)) obj = "9行目　「生年月日」";
        else if ("table.h9.INSURED_NO".equals(code)) obj = "9行目　「被保険者番号」";
        else if ("table.h9.DR_NM".equals(code)) obj = "9行目　「医師名」";
        else if ("table.h9.REQ_DT".equals(code)) obj = "9行目　「作成依頼日」";
        else if ("table.h9.KINYU_DT".equals(code)) obj = "9行目　「意見書記入日」";
        else if ("table.h9.SEND_DT".equals(code)) obj = "9行目　「意見書送付日」";
        else if ("table.h10.HAKKOU_KBN".equals(code)) obj = "10行目　「請求書」";
        else if ("table.h10.PATIENT_NM".equals(code)) obj = "10行目　「氏名」";
        else if ("table.h10.PATIENT_KN".equals(code)) obj = "10行目　「ふりがな」";
        else if ("table.h10.SEX".equals(code)) obj = "10行目　「性別」";
        else if ("table.h10.AGE".equals(code)) obj = "10行目　「年齢」";
        else if ("table.h10.BIRTHDAY".equals(code)) obj = "10行目　「生年月日」";
        else if ("table.h10.INSURED_NO".equals(code)) obj = "10行目　「被保険者番号」";
        else if ("table.h10.DR_NM".equals(code)) obj = "10行目　「医師名」";
        else if ("table.h10.REQ_DT".equals(code)) obj = "10行目　「作成依頼日」";
        else if ("table.h10.KINYU_DT".equals(code)) obj = "10行目　「意見書記入日」";
        else if ("table.h10.SEND_DT".equals(code)) obj = "10行目　「意見書送付日」";
        else if ("table.h11.HAKKOU_KBN".equals(code)) obj = "11行目　「請求書」";
        else if ("table.h11.PATIENT_NM".equals(code)) obj = "11行目　「氏名」";
        else if ("table.h11.PATIENT_KN".equals(code)) obj = "11行目　「ふりがな」";
        else if ("table.h11.SEX".equals(code)) obj = "11行目　「性別」";
        else if ("table.h11.AGE".equals(code)) obj = "11行目　「年齢」";
        else if ("table.h11.BIRTHDAY".equals(code)) obj = "11行目　「生年月日」";
        else if ("table.h11.INSURED_NO".equals(code)) obj = "11行目　「被保険者番号」";
        else if ("table.h11.DR_NM".equals(code)) obj = "11行目　「医師名」";
        else if ("table.h11.REQ_DT".equals(code)) obj = "11行目　「作成依頼日」";
        else if ("table.h11.KINYU_DT".equals(code)) obj = "11行目　「意見書記入日」";
        else if ("table.h11.SEND_DT".equals(code)) obj = "11行目　「意見書送付日」";
        else if ("table.h12.HAKKOU_KBN".equals(code)) obj = "12行目　「請求書」";
        else if ("table.h12.PATIENT_NM".equals(code)) obj = "12行目　「氏名」";
        else if ("table.h12.PATIENT_KN".equals(code)) obj = "12行目　「ふりがな」";
        else if ("table.h12.SEX".equals(code)) obj = "12行目　「性別」";
        else if ("table.h12.AGE".equals(code)) obj = "12行目　「年齢」";
        else if ("table.h12.BIRTHDAY".equals(code)) obj = "12行目　「生年月日」";
        else if ("table.h12.INSURED_NO".equals(code)) obj = "12行目　「被保険者番号」";
        else if ("table.h12.DR_NM".equals(code)) obj = "12行目　「医師名」";
        else if ("table.h12.REQ_DT".equals(code)) obj = "12行目　「作成依頼日」";
        else if ("table.h12.KINYU_DT".equals(code)) obj = "12行目　「意見書記入日」";
        else if ("table.h12.SEND_DT".equals(code)) obj = "12行目　「意見書送付日」";
        else if ("table.h13.HAKKOU_KBN".equals(code)) obj = "13行目　「請求書」";
        else if ("table.h13.PATIENT_NM".equals(code)) obj = "13行目　「氏名」";
        else if ("table.h13.PATIENT_KN".equals(code)) obj = "13行目　「ふりがな」";
        else if ("table.h13.SEX".equals(code)) obj = "13行目　「性別」";
        else if ("table.h13.AGE".equals(code)) obj = "13行目　「年齢」";
        else if ("table.h13.BIRTHDAY".equals(code)) obj = "13行目　「生年月日」";
        else if ("table.h13.INSURED_NO".equals(code)) obj = "13行目　「被保険者番号」";
        else if ("table.h13.DR_NM".equals(code)) obj = "13行目　「医師名」";
        else if ("table.h13.REQ_DT".equals(code)) obj = "13行目　「作成依頼日」";
        else if ("table.h13.KINYU_DT".equals(code)) obj = "13行目　「意見書記入日」";
        else if ("table.h13.SEND_DT".equals(code)) obj = "13行目　「意見書送付日」";
        else if ("table.h14.HAKKOU_KBN".equals(code)) obj = "14行目　「請求書」";
        else if ("table.h14.PATIENT_NM".equals(code)) obj = "14行目　「氏名」";
        else if ("table.h14.PATIENT_KN".equals(code)) obj = "14行目　「ふりがな」";
        else if ("table.h14.SEX".equals(code)) obj = "14行目　「性別」";
        else if ("table.h14.AGE".equals(code)) obj = "14行目　「年齢」";
        else if ("table.h14.BIRTHDAY".equals(code)) obj = "14行目　「生年月日」";
        else if ("table.h14.INSURED_NO".equals(code)) obj = "14行目　「被保険者番号」";
        else if ("table.h14.DR_NM".equals(code)) obj = "14行目　「医師名」";
        else if ("table.h14.REQ_DT".equals(code)) obj = "14行目　「作成依頼日」";
        else if ("table.h14.KINYU_DT".equals(code)) obj = "14行目　「意見書記入日」";
        else if ("table.h14.SEND_DT".equals(code)) obj = "14行目　「意見書送付日」";
        else if ("table.h15.HAKKOU_KBN".equals(code)) obj = "15行目　「請求書」";
        else if ("table.h15.PATIENT_NM".equals(code)) obj = "15行目　「氏名」";
        else if ("table.h15.PATIENT_KN".equals(code)) obj = "15行目　「ふりがな」";
        else if ("table.h15.SEX".equals(code)) obj = "15行目　「性別」";
        else if ("table.h15.AGE".equals(code)) obj = "15行目　「年齢」";
        else if ("table.h15.BIRTHDAY".equals(code)) obj = "15行目　「生年月日」";
        else if ("table.h15.INSURED_NO".equals(code)) obj = "15行目　「被保険者番号」";
        else if ("table.h15.DR_NM".equals(code)) obj = "15行目　「医師名」";
        else if ("table.h15.REQ_DT".equals(code)) obj = "15行目　「作成依頼日」";
        else if ("table.h15.KINYU_DT".equals(code)) obj = "15行目　「意見書記入日」";
        else if ("table.h15.SEND_DT".equals(code)) obj = "15行目　「意見書送付日」";
        else if ("table.h16.HAKKOU_KBN".equals(code)) obj = "16行目　「請求書」";
        else if ("table.h16.PATIENT_NM".equals(code)) obj = "16行目　「氏名」";
        else if ("table.h16.PATIENT_KN".equals(code)) obj = "16行目　「ふりがな」";
        else if ("table.h16.SEX".equals(code)) obj = "16行目　「性別」";
        else if ("table.h16.AGE".equals(code)) obj = "16行目　「年齢」";
        else if ("table.h16.BIRTHDAY".equals(code)) obj = "16行目　「生年月日」";
        else if ("table.h16.INSURED_NO".equals(code)) obj = "16行目　「被保険者番号」";
        else if ("table.h16.DR_NM".equals(code)) obj = "16行目　「医師名」";
        else if ("table.h16.REQ_DT".equals(code)) obj = "16行目　「作成依頼日」";
        else if ("table.h16.KINYU_DT".equals(code)) obj = "16行目　「意見書記入日」";
        else if ("table.h16.SEND_DT".equals(code)) obj = "16行目　「意見書送付日」";
        else if ("table.h17.HAKKOU_KBN".equals(code)) obj = "17行目　「請求書」";
        else if ("table.h17.PATIENT_NM".equals(code)) obj = "17行目　「氏名」";
        else if ("table.h17.PATIENT_KN".equals(code)) obj = "17行目　「ふりがな」";
        else if ("table.h17.SEX".equals(code)) obj = "17行目　「性別」";
        else if ("table.h17.AGE".equals(code)) obj = "17行目　「年齢」";
        else if ("table.h17.BIRTHDAY".equals(code)) obj = "17行目　「生年月日」";
        else if ("table.h17.INSURED_NO".equals(code)) obj = "17行目　「被保険者番号」";
        else if ("table.h17.DR_NM".equals(code)) obj = "17行目　「医師名」";
        else if ("table.h17.REQ_DT".equals(code)) obj = "17行目　「作成依頼日」";
        else if ("table.h17.KINYU_DT".equals(code)) obj = "17行目　「意見書記入日」";
        else if ("table.h17.SEND_DT".equals(code)) obj = "17行目　「意見書送付日」";
        else if ("table.h18.HAKKOU_KBN".equals(code)) obj = "18行目　「請求書」";
        else if ("table.h18.PATIENT_NM".equals(code)) obj = "18行目　「氏名」";
        else if ("table.h18.PATIENT_KN".equals(code)) obj = "18行目　「ふりがな」";
        else if ("table.h18.SEX".equals(code)) obj = "18行目　「性別」";
        else if ("table.h18.AGE".equals(code)) obj = "18行目　「年齢」";
        else if ("table.h18.BIRTHDAY".equals(code)) obj = "18行目　「生年月日」";
        else if ("table.h18.INSURED_NO".equals(code)) obj = "18行目　「被保険者番号」";
        else if ("table.h18.DR_NM".equals(code)) obj = "18行目　「医師名」";
        else if ("table.h18.REQ_DT".equals(code)) obj = "18行目　「作成依頼日」";
        else if ("table.h18.KINYU_DT".equals(code)) obj = "18行目　「意見書記入日」";
        else if ("table.h18.SEND_DT".equals(code)) obj = "18行目　「意見書送付日」";
        else if ("table.h19.HAKKOU_KBN".equals(code)) obj = "19行目　「請求書」";
        else if ("table.h19.PATIENT_NM".equals(code)) obj = "19行目　「氏名」";
        else if ("table.h19.PATIENT_KN".equals(code)) obj = "19行目　「ふりがな」";
        else if ("table.h19.SEX".equals(code)) obj = "19行目　「性別」";
        else if ("table.h19.AGE".equals(code)) obj = "19行目　「年齢」";
        else if ("table.h19.BIRTHDAY".equals(code)) obj = "19行目　「生年月日」";
        else if ("table.h19.INSURED_NO".equals(code)) obj = "19行目　「被保険者番号」";
        else if ("table.h19.DR_NM".equals(code)) obj = "19行目　「医師名」";
        else if ("table.h19.REQ_DT".equals(code)) obj = "19行目　「作成依頼日」";
        else if ("table.h19.KINYU_DT".equals(code)) obj = "19行目　「意見書記入日」";
        else if ("table.h19.SEND_DT".equals(code)) obj = "19行目　「意見書送付日」";
        else if ("table.h20.HAKKOU_KBN".equals(code)) obj = "20行目　「請求書」";
        else if ("table.h20.PATIENT_NM".equals(code)) obj = "20行目　「氏名」";
        else if ("table.h20.PATIENT_KN".equals(code)) obj = "20行目　「ふりがな」";
        else if ("table.h20.SEX".equals(code)) obj = "20行目　「性別」";
        else if ("table.h20.AGE".equals(code)) obj = "20行目　「年齢」";
        else if ("table.h20.BIRTHDAY".equals(code)) obj = "20行目　「生年月日」";
        else if ("table.h20.INSURED_NO".equals(code)) obj = "20行目　「被保険者番号」";
        else if ("table.h20.DR_NM".equals(code)) obj = "20行目　「医師名」";
        else if ("table.h20.REQ_DT".equals(code)) obj = "20行目　「作成依頼日」";
        else if ("table.h20.KINYU_DT".equals(code)) obj = "20行目　「意見書記入日」";
        else if ("table.h20.SEND_DT".equals(code)) obj = "20行目　「意見書送付日」";
        else if ("table.h21.HAKKOU_KBN".equals(code)) obj = "21行目　「請求書」";
        else if ("table.h21.PATIENT_NM".equals(code)) obj = "21行目　「氏名」";
        else if ("table.h21.PATIENT_KN".equals(code)) obj = "21行目　「ふりがな」";
        else if ("table.h21.SEX".equals(code)) obj = "21行目　「性別」";
        else if ("table.h21.AGE".equals(code)) obj = "21行目　「年齢」";
        else if ("table.h21.BIRTHDAY".equals(code)) obj = "21行目　「生年月日」";
        else if ("table.h21.INSURED_NO".equals(code)) obj = "21行目　「被保険者番号」";
        else if ("table.h21.DR_NM".equals(code)) obj = "21行目　「医師名」";
        else if ("table.h21.REQ_DT".equals(code)) obj = "21行目　「作成依頼日」";
        else if ("table.h21.KINYU_DT".equals(code)) obj = "21行目　「意見書記入日」";
        else if ("table.h21.SEND_DT".equals(code)) obj = "21行目　「意見書送付日」";
        return obj;
    }
    
    /**
     * ＣＳＶファイル提出患者一覧の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatCSVList(String code, Object obj){
        if ("lblTitle".equals(code)) obj = "タイトル";
        else if ("formatVersion".equals(code)) obj = "フォーマットバージョン";
        else if ("date".equals(code)) obj = "印刷日";
        else if ("hokensya".equals(code)) obj = "保険者名";
        else if ("dateRange".equals(code)) obj = "記入日";
        else if ("table".equals(code)) obj = "一覧";
        else if ("table.title.DR_NM".equals(code)) obj = "列名「医師名」";
        else if ("table.h1.DR_NM".equals(code)) obj = "1行目「医師名」";
        else if ("table.h2.DR_NM".equals(code)) obj = "2行目「医師名」";
        else if ("table.h3.DR_NM".equals(code)) obj = "3行目「医師名」";
        else if ("table.h4.DR_NM".equals(code)) obj = "4行目「医師名」";
        else if ("table.h5.DR_NM".equals(code)) obj = "5行目「医師名」";
        else if ("table.h6.DR_NM".equals(code)) obj = "6行目「医師名」";
        else if ("table.h7.DR_NM".equals(code)) obj = "7行目「医師名」";
        else if ("table.h8.DR_NM".equals(code)) obj = "8行目「医師名」";
        else if ("table.h9.DR_NM".equals(code)) obj = "9行目「医師名」";
        else if ("table.h10.DR_NM".equals(code)) obj = "10行目「医師名」";
        else if ("table.h11.DR_NM".equals(code)) obj = "11行目「医師名」";
        else if ("table.h12.DR_NM".equals(code)) obj = "12行目「医師名」";
        else if ("table.h13.DR_NM".equals(code)) obj = "13行目「医師名」";
        else if ("table.h14.DR_NM".equals(code)) obj = "14行目「医師名」";
        else if ("table.h15.DR_NM".equals(code)) obj = "15行目「医師名」";
        else if ("table.h16.DR_NM".equals(code)) obj = "16行目「医師名」";
        else if ("table.h17.DR_NM".equals(code)) obj = "17行目「医師名」";
        else if ("table.h18.DR_NM".equals(code)) obj = "18行目「医師名」";
        else if ("table.h19.DR_NM".equals(code)) obj = "19行目「医師名」";
        else if ("table.h20.DR_NM".equals(code)) obj = "20行目「医師名」";
        else if ("table.h21.DR_NM".equals(code)) obj = "21行目「医師名」";
        else if ("table.title.PATIENT_NM".equals(code)) obj = "列名「氏名」";
        else if ("table.h1.PATIENT_NM".equals(code)) obj = "1行目「氏名」";
        else if ("table.h2.PATIENT_NM".equals(code)) obj = "2行目「氏名」";
        else if ("table.h3.PATIENT_NM".equals(code)) obj = "3行目「氏名」";
        else if ("table.h4.PATIENT_NM".equals(code)) obj = "4行目「氏名」";
        else if ("table.h5.PATIENT_NM".equals(code)) obj = "5行目「氏名」";
        else if ("table.h6.PATIENT_NM".equals(code)) obj = "6行目「氏名」";
        else if ("table.h7.PATIENT_NM".equals(code)) obj = "7行目「氏名」";
        else if ("table.h8.PATIENT_NM".equals(code)) obj = "8行目「氏名」";
        else if ("table.h9.PATIENT_NM".equals(code)) obj = "9行目「氏名」";
        else if ("table.h10.PATIENT_NM".equals(code)) obj = "10行目「氏名」";
        else if ("table.h11.PATIENT_NM".equals(code)) obj = "11行目「氏名」";
        else if ("table.h12.PATIENT_NM".equals(code)) obj = "12行目「氏名」";
        else if ("table.h13.PATIENT_NM".equals(code)) obj = "13行目「氏名」";
        else if ("table.h14.PATIENT_NM".equals(code)) obj = "14行目「氏名」";
        else if ("table.h15.PATIENT_NM".equals(code)) obj = "15行目「氏名」";
        else if ("table.h16.PATIENT_NM".equals(code)) obj = "16行目「氏名」";
        else if ("table.h17.PATIENT_NM".equals(code)) obj = "17行目「氏名」";
        else if ("table.h18.PATIENT_NM".equals(code)) obj = "18行目「氏名」";
        else if ("table.h19.PATIENT_NM".equals(code)) obj = "19行目「氏名」";
        else if ("table.h20.PATIENT_NM".equals(code)) obj = "20行目「氏名」";
        else if ("table.h21.PATIENT_NM".equals(code)) obj = "21行目「氏名」";
        else if ("table.title.PATIENT_KN".equals(code)) obj = "列名「ふりがな」";
        else if ("table.h1.PATIENT_KN".equals(code)) obj = "1行目「ふりがな」";
        else if ("table.h2.PATIENT_KN".equals(code)) obj = "2行目「ふりがな」";
        else if ("table.h3.PATIENT_KN".equals(code)) obj = "3行目「ふりがな」";
        else if ("table.h4.PATIENT_KN".equals(code)) obj = "4行目「ふりがな」";
        else if ("table.h5.PATIENT_KN".equals(code)) obj = "5行目「ふりがな」";
        else if ("table.h6.PATIENT_KN".equals(code)) obj = "6行目「ふりがな」";
        else if ("table.h7.PATIENT_KN".equals(code)) obj = "7行目「ふりがな」";
        else if ("table.h8.PATIENT_KN".equals(code)) obj = "8行目「ふりがな」";
        else if ("table.h9.PATIENT_KN".equals(code)) obj = "9行目「ふりがな」";
        else if ("table.h10.PATIENT_KN".equals(code)) obj = "10行目「ふりがな」";
        else if ("table.h11.PATIENT_KN".equals(code)) obj = "11行目「ふりがな」";
        else if ("table.h12.PATIENT_KN".equals(code)) obj = "12行目「ふりがな」";
        else if ("table.h13.PATIENT_KN".equals(code)) obj = "13行目「ふりがな」";
        else if ("table.h14.PATIENT_KN".equals(code)) obj = "14行目「ふりがな」";
        else if ("table.h15.PATIENT_KN".equals(code)) obj = "15行目「ふりがな」";
        else if ("table.h16.PATIENT_KN".equals(code)) obj = "16行目「ふりがな」";
        else if ("table.h17.PATIENT_KN".equals(code)) obj = "17行目「ふりがな」";
        else if ("table.h18.PATIENT_KN".equals(code)) obj = "18行目「ふりがな」";
        else if ("table.h19.PATIENT_KN".equals(code)) obj = "19行目「ふりがな」";
        else if ("table.h20.PATIENT_KN".equals(code)) obj = "20行目「ふりがな」";
        else if ("table.h21.PATIENT_KN".equals(code)) obj = "21行目「ふりがな」";
        else if ("table.title.SEX".equals(code)) obj = "列名「性別」";
        else if ("table.h1.SEX".equals(code)) obj = "1行目「性別」";
        else if ("table.h2.SEX".equals(code)) obj = "2行目「性別」";
        else if ("table.h3.SEX".equals(code)) obj = "3行目「性別」";
        else if ("table.h4.SEX".equals(code)) obj = "4行目「性別」";
        else if ("table.h5.SEX".equals(code)) obj = "5行目「性別」";
        else if ("table.h6.SEX".equals(code)) obj = "6行目「性別」";
        else if ("table.h7.SEX".equals(code)) obj = "7行目「性別」";
        else if ("table.h8.SEX".equals(code)) obj = "8行目「性別」";
        else if ("table.h9.SEX".equals(code)) obj = "9行目「性別」";
        else if ("table.h10.SEX".equals(code)) obj = "10行目「性別」";
        else if ("table.h11.SEX".equals(code)) obj = "11行目「性別」";
        else if ("table.h12.SEX".equals(code)) obj = "12行目「性別」";
        else if ("table.h13.SEX".equals(code)) obj = "13行目「性別」";
        else if ("table.h14.SEX".equals(code)) obj = "14行目「性別」";
        else if ("table.h15.SEX".equals(code)) obj = "15行目「性別」";
        else if ("table.h16.SEX".equals(code)) obj = "16行目「性別」";
        else if ("table.h17.SEX".equals(code)) obj = "17行目「性別」";
        else if ("table.h18.SEX".equals(code)) obj = "18行目「性別」";
        else if ("table.h19.SEX".equals(code)) obj = "19行目「性別」";
        else if ("table.h20.SEX".equals(code)) obj = "20行目「性別」";
        else if ("table.h21.SEX".equals(code)) obj = "21行目「性別」";
        else if ("table.title.AGE".equals(code)) obj = "列名「年齢」";
        else if ("table.h1.AGE".equals(code)) obj = "1行目「年齢」";
        else if ("table.h2.AGE".equals(code)) obj = "2行目「年齢」";
        else if ("table.h3.AGE".equals(code)) obj = "3行目「年齢」";
        else if ("table.h4.AGE".equals(code)) obj = "4行目「年齢」";
        else if ("table.h5.AGE".equals(code)) obj = "5行目「年齢」";
        else if ("table.h6.AGE".equals(code)) obj = "6行目「年齢」";
        else if ("table.h7.AGE".equals(code)) obj = "7行目「年齢」";
        else if ("table.h8.AGE".equals(code)) obj = "8行目「年齢」";
        else if ("table.h9.AGE".equals(code)) obj = "9行目「年齢」";
        else if ("table.h10.AGE".equals(code)) obj = "10行目「年齢」";
        else if ("table.h11.AGE".equals(code)) obj = "11行目「年齢」";
        else if ("table.h12.AGE".equals(code)) obj = "12行目「年齢」";
        else if ("table.h13.AGE".equals(code)) obj = "13行目「年齢」";
        else if ("table.h14.AGE".equals(code)) obj = "14行目「年齢」";
        else if ("table.h15.AGE".equals(code)) obj = "15行目「年齢」";
        else if ("table.h16.AGE".equals(code)) obj = "16行目「年齢」";
        else if ("table.h17.AGE".equals(code)) obj = "17行目「年齢」";
        else if ("table.h18.AGE".equals(code)) obj = "18行目「年齢」";
        else if ("table.h19.AGE".equals(code)) obj = "19行目「年齢」";
        else if ("table.h20.AGE".equals(code)) obj = "20行目「年齢」";
        else if ("table.h21.AGE".equals(code)) obj = "21行目「年齢」";
        else if ("table.title.BIRTHDAY".equals(code)) obj = "列名「生年月日」";
        else if ("table.h1.BIRTHDAY".equals(code)) obj = "1行目「生年月日」";
        else if ("table.h2.BIRTHDAY".equals(code)) obj = "2行目「生年月日」";
        else if ("table.h3.BIRTHDAY".equals(code)) obj = "3行目「生年月日」";
        else if ("table.h4.BIRTHDAY".equals(code)) obj = "4行目「生年月日」";
        else if ("table.h5.BIRTHDAY".equals(code)) obj = "5行目「生年月日」";
        else if ("table.h6.BIRTHDAY".equals(code)) obj = "6行目「生年月日」";
        else if ("table.h7.BIRTHDAY".equals(code)) obj = "7行目「生年月日」";
        else if ("table.h8.BIRTHDAY".equals(code)) obj = "8行目「生年月日」";
        else if ("table.h9.BIRTHDAY".equals(code)) obj = "9行目「生年月日」";
        else if ("table.h10.BIRTHDAY".equals(code)) obj = "10行目「生年月日」";
        else if ("table.h11.BIRTHDAY".equals(code)) obj = "11行目「生年月日」";
        else if ("table.h12.BIRTHDAY".equals(code)) obj = "12行目「生年月日」";
        else if ("table.h13.BIRTHDAY".equals(code)) obj = "13行目「生年月日」";
        else if ("table.h14.BIRTHDAY".equals(code)) obj = "14行目「生年月日」";
        else if ("table.h15.BIRTHDAY".equals(code)) obj = "15行目「生年月日」";
        else if ("table.h16.BIRTHDAY".equals(code)) obj = "16行目「生年月日」";
        else if ("table.h17.BIRTHDAY".equals(code)) obj = "17行目「生年月日」";
        else if ("table.h18.BIRTHDAY".equals(code)) obj = "18行目「生年月日」";
        else if ("table.h19.BIRTHDAY".equals(code)) obj = "19行目「生年月日」";
        else if ("table.h20.BIRTHDAY".equals(code)) obj = "20行目「生年月日」";
        else if ("table.h21.BIRTHDAY".equals(code)) obj = "21行目「生年月日」";
        else if ("table.title.INSURED_NO".equals(code)) obj = "列名「被保険者番号」";
        else if ("table.h1.INSURED_NO".equals(code)) obj = "1行目「被保険者番号」";
        else if ("table.h2.INSURED_NO".equals(code)) obj = "2行目「被保険者番号」";
        else if ("table.h3.INSURED_NO".equals(code)) obj = "3行目「被保険者番号」";
        else if ("table.h4.INSURED_NO".equals(code)) obj = "4行目「被保険者番号」";
        else if ("table.h5.INSURED_NO".equals(code)) obj = "5行目「被保険者番号」";
        else if ("table.h6.INSURED_NO".equals(code)) obj = "6行目「被保険者番号」";
        else if ("table.h7.INSURED_NO".equals(code)) obj = "7行目「被保険者番号」";
        else if ("table.h8.INSURED_NO".equals(code)) obj = "8行目「被保険者番号」";
        else if ("table.h9.INSURED_NO".equals(code)) obj = "9行目「被保険者番号」";
        else if ("table.h10.INSURED_NO".equals(code)) obj = "10行目「被保険者番号」";
        else if ("table.h11.INSURED_NO".equals(code)) obj = "11行目「被保険者番号」";
        else if ("table.h12.INSURED_NO".equals(code)) obj = "12行目「被保険者番号」";
        else if ("table.h13.INSURED_NO".equals(code)) obj = "13行目「被保険者番号」";
        else if ("table.h14.INSURED_NO".equals(code)) obj = "14行目「被保険者番号」";
        else if ("table.h15.INSURED_NO".equals(code)) obj = "15行目「被保険者番号」";
        else if ("table.h16.INSURED_NO".equals(code)) obj = "16行目「被保険者番号」";
        else if ("table.h17.INSURED_NO".equals(code)) obj = "17行目「被保険者番号」";
        else if ("table.h18.INSURED_NO".equals(code)) obj = "18行目「被保険者番号」";
        else if ("table.h19.INSURED_NO".equals(code)) obj = "19行目「被保険者番号」";
        else if ("table.h20.INSURED_NO".equals(code)) obj = "20行目「被保険者番号」";
        else if ("table.h21.INSURED_NO".equals(code)) obj = "21行目「被保険者番号」";
        else if ("table.title.FD_TIMESTAMP".equals(code)) obj = "列名「タイムスタンプ」";
        else if ("table.h1.FD_TIMESTAMP".equals(code)) obj = "1行目「タイムスタンプ」";
        else if ("table.h2.FD_TIMESTAMP".equals(code)) obj = "2行目「タイムスタンプ」";
        else if ("table.h3.FD_TIMESTAMP".equals(code)) obj = "3行目「タイムスタンプ」";
        else if ("table.h4.FD_TIMESTAMP".equals(code)) obj = "4行目「タイムスタンプ」";
        else if ("table.h5.FD_TIMESTAMP".equals(code)) obj = "5行目「タイムスタンプ」";
        else if ("table.h6.FD_TIMESTAMP".equals(code)) obj = "6行目「タイムスタンプ」";
        else if ("table.h7.FD_TIMESTAMP".equals(code)) obj = "7行目「タイムスタンプ」";
        else if ("table.h8.FD_TIMESTAMP".equals(code)) obj = "8行目「タイムスタンプ」";
        else if ("table.h9.FD_TIMESTAMP".equals(code)) obj = "9行目「タイムスタンプ」";
        else if ("table.h10.FD_TIMESTAMP".equals(code)) obj = "10行目「タイムスタンプ」";
        else if ("table.h11.FD_TIMESTAMP".equals(code)) obj = "11行目「タイムスタンプ」";
        else if ("table.h12.FD_TIMESTAMP".equals(code)) obj = "12行目「タイムスタンプ」";
        else if ("table.h13.FD_TIMESTAMP".equals(code)) obj = "13行目「タイムスタンプ」";
        else if ("table.h14.FD_TIMESTAMP".equals(code)) obj = "14行目「タイムスタンプ」";
        else if ("table.h15.FD_TIMESTAMP".equals(code)) obj = "15行目「タイムスタンプ」";
        else if ("table.h16.FD_TIMESTAMP".equals(code)) obj = "16行目「タイムスタンプ」";
        else if ("table.h17.FD_TIMESTAMP".equals(code)) obj = "17行目「タイムスタンプ」";
        else if ("table.h18.FD_TIMESTAMP".equals(code)) obj = "18行目「タイムスタンプ」";
        else if ("table.h19.FD_TIMESTAMP".equals(code)) obj = "19行目「タイムスタンプ」";
        else if ("table.h20.FD_TIMESTAMP".equals(code)) obj = "20行目「タイムスタンプ」";
        else if ("table.h21.FD_TIMESTAMP".equals(code)) obj = "21行目「タイムスタンプ」";
        else if ("table.title.REQ_DT".equals(code)) obj = "列名「作成依頼日」";
        else if ("table.h1.REQ_DT".equals(code)) obj = "1行目「作成依頼日」";
        else if ("table.h2.REQ_DT".equals(code)) obj = "2行目「作成依頼日」";
        else if ("table.h3.REQ_DT".equals(code)) obj = "3行目「作成依頼日」";
        else if ("table.h4.REQ_DT".equals(code)) obj = "4行目「作成依頼日」";
        else if ("table.h5.REQ_DT".equals(code)) obj = "5行目「作成依頼日」";
        else if ("table.h6.REQ_DT".equals(code)) obj = "6行目「作成依頼日」";
        else if ("table.h7.REQ_DT".equals(code)) obj = "7行目「作成依頼日」";
        else if ("table.h8.REQ_DT".equals(code)) obj = "8行目「作成依頼日」";
        else if ("table.h9.REQ_DT".equals(code)) obj = "9行目「作成依頼日」";
        else if ("table.h10.REQ_DT".equals(code)) obj = "10行目「作成依頼日」";
        else if ("table.h11.REQ_DT".equals(code)) obj = "11行目「作成依頼日」";
        else if ("table.h12.REQ_DT".equals(code)) obj = "12行目「作成依頼日」";
        else if ("table.h13.REQ_DT".equals(code)) obj = "13行目「作成依頼日」";
        else if ("table.h14.REQ_DT".equals(code)) obj = "14行目「作成依頼日」";
        else if ("table.h15.REQ_DT".equals(code)) obj = "15行目「作成依頼日」";
        else if ("table.h16.REQ_DT".equals(code)) obj = "16行目「作成依頼日」";
        else if ("table.h17.REQ_DT".equals(code)) obj = "17行目「作成依頼日」";
        else if ("table.h18.REQ_DT".equals(code)) obj = "18行目「作成依頼日」";
        else if ("table.h19.REQ_DT".equals(code)) obj = "19行目「作成依頼日」";
        else if ("table.h20.REQ_DT".equals(code)) obj = "20行目「作成依頼日」";
        else if ("table.h21.REQ_DT".equals(code)) obj = "21行目「作成依頼日」";
        else if ("table.title.KINYU_DT".equals(code)) obj = "列名「意見書記入日」";
        else if ("table.h1.KINYU_DT".equals(code)) obj = "1行目「意見書記入日」";
        else if ("table.h2.KINYU_DT".equals(code)) obj = "2行目「意見書記入日」";
        else if ("table.h3.KINYU_DT".equals(code)) obj = "3行目「意見書記入日」";
        else if ("table.h4.KINYU_DT".equals(code)) obj = "4行目「意見書記入日」";
        else if ("table.h5.KINYU_DT".equals(code)) obj = "5行目「意見書記入日」";
        else if ("table.h6.KINYU_DT".equals(code)) obj = "6行目「意見書記入日」";
        else if ("table.h7.KINYU_DT".equals(code)) obj = "7行目「意見書記入日」";
        else if ("table.h8.KINYU_DT".equals(code)) obj = "8行目「意見書記入日」";
        else if ("table.h9.KINYU_DT".equals(code)) obj = "9行目「意見書記入日」";
        else if ("table.h10.KINYU_DT".equals(code)) obj = "10行目「意見書記入日」";
        else if ("table.h11.KINYU_DT".equals(code)) obj = "11行目「意見書記入日」";
        else if ("table.h12.KINYU_DT".equals(code)) obj = "12行目「意見書記入日」";
        else if ("table.h13.KINYU_DT".equals(code)) obj = "13行目「意見書記入日」";
        else if ("table.h14.KINYU_DT".equals(code)) obj = "14行目「意見書記入日」";
        else if ("table.h15.KINYU_DT".equals(code)) obj = "15行目「意見書記入日」";
        else if ("table.h16.KINYU_DT".equals(code)) obj = "16行目「意見書記入日」";
        else if ("table.h17.KINYU_DT".equals(code)) obj = "17行目「意見書記入日」";
        else if ("table.h18.KINYU_DT".equals(code)) obj = "18行目「意見書記入日」";
        else if ("table.h19.KINYU_DT".equals(code)) obj = "19行目「意見書記入日」";
        else if ("table.h20.KINYU_DT".equals(code)) obj = "20行目「意見書記入日」";
        else if ("table.h21.KINYU_DT".equals(code)) obj = "21行目「意見書記入日」";
        else if ("page".equals(code)) obj = "ページ番号";

        return obj;
    }

    /**
     * 医師意見書1ページ目の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatIkenshoShien1(String code, Object obj){
        // 医師意見書1ページ目
        if ("Label27".equals(code)) obj = "申請者情報・性別「女」　見出し";
        else if ("Label26".equals(code)) obj = "申請者情報・性別「男」　見出し";
        else if ("Label25".equals(code)) obj = "申請者情報・生年月日「昭和」　見出し";
        else if ("Label24".equals(code)) obj = "申請者情報・生年月日「大正」　見出し";
        else if ("Label23".equals(code)) obj = "申請者情報・生年月日「明治」　見出し";
        else if ("PatienBirthShowa".equals(code)) obj = "申請者情報・生年月日「昭和」　○";
        else if ("PatienBirthTaisho".equals(code)) obj = "申請者情報・利用者生年月日「大正」　○";
        else if ("Label21".equals(code)) obj = "１.傷病に関する意見・｢（1）診断名」　見出し2";
        else if ("Label16".equals(code)) obj = "医療機関所在地　下線";
        else if ("Label15".equals(code)) obj = "医療機関名　下線";
        else if ("Label14".equals(code)) obj = "医師氏名　下線";
        else if ("Label1".equals(code)) obj = "３.心身の状態に関する意見・「(１)行動上の障害の有無 （該当する項目全てチェック）｣　見出し";
        else if ("Label20".equals(code)) obj = "医師意見書　見出し";
        else if ("Label6".equals(code)) obj = "医師意見書　ページ数";
        else if ("Label8".equals(code)) obj = "３.心身の状態に関する意見　見出し1";
        else if ("Label9".equals(code)) obj = "１. 傷病に関する意見 見出し";
        else if ("Label10".equals(code)) obj = "２. 特別な医療　見出し2";
        else if ("Label11".equals(code)) obj = "２. 特別な医療　見出し1";
        else if ("PatienBirthMeiji".equals(code)) obj = "申請者情報・生年月日「明治」　○";
        else if ("PatientSexMale".equals(code)) obj = "申請者情報・性別「男」　○";
        else if ("PatientSexFemale".equals(code)) obj = "申請者情報・性別「女」　○";
        else if ("Shape93".equals(code)) obj = "記入日　下線";
        else if ("ShobyoKeika".equals(code)) obj = "１.傷病に関する意見・「障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容」　内容";
        else if ("ShojoFuanteiJokyo".equals(code)) obj = "１.傷病に関する意見・「(２) 症状としての安定性」　内容";
        else if ("Label5".equals(code)) obj = "１. 傷病に関する意見・「入院歴」　見出し";
        else if ("Label87".equals(code)) obj = "３．心身の状態に関する意見・「専門医受診の有無」　見出し";
        else if ("SeishinShinkeiName".equals(code)) obj = "３．心身の状態に関する意見・「(２) 精神･神経症状の有無」　症状名";
        else if ("SeishinShinkeiSenmoniName".equals(code)) obj = "３．心身の状態に関する意見・「専門医受診の有無」　内容";
        else if ("SeishinShinkeiOtherName".equals(code)) obj = "３．心身の状態に関する意見・「(２) 精神･神経症状の有無（その他）」　症状名";
        else if ("Label18".equals(code)) obj = "申請者情報・生年月日「平成」　見出し";
        else if ("PatienBirthHeisei".equals(code)) obj = "申請者情報・生年月日「平成」　○";
        else if ("INSURER_NO".equals(code)) obj = "保険者番号";
        // [ID:0000555][Tozo TANAKA] 2009/09/14 replace begin 【2009年度対応：追加案件】医師意見書の受給者番号対応
//        else if ("INSURED_NO".equals(code)) obj = "被保険者番号";
        else if ("INSURED_NO".equals(code)) obj = "受給者番号";
        // [ID:0000555][Tozo TANAKA] 2009/09/14 replace end 【2009年度対応：追加案件】医師意見書の受給者番号対応
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add begin 【2009年度対応：追加案件】医師意見書の受給者番号対応
        else if ("INSURER_NO_LABEL".equals(code)) obj = "保険者番号 見出し";
        else if ("INSURERD_NO_LABEL".equals(code)) obj = "受給者番号 見出し";
        else if ("FD_OUTPUT_TIME_LABEL".equals(code)) obj = "作成日時 見出し";
        else if ("Label113".equals(code)) obj = "在宅・施設区分";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add end 【2009年度対応：追加案件】医師意見書の受給者番号対応
        else if ("SeishinShinkeiKiokuShogaiTanki".equals(code)) obj = "３. 心身の状態に関する意見・「(２) 精神･神経症状の有無（短期）」　○";
        else if ("SeishinShinkeiKiokuShogaiChouki".equals(code)) obj = "３. 心身の状態に関する意見・「(２) 精神･神経症状の有無（長期）」　○";
        else if ("ShochiKyuinCount".equals(code)) obj = "２. 特別な医療・「吸引処置　回数」";
        else if ("shussei1".equals(code)) obj = "１. 傷病に関する意見・「出生時1」";
        else if ("shussei2".equals(code)) obj = "１. 傷病に関する意見・「出生時2」";
        else if ("shussei3".equals(code)) obj = "１. 傷病に関する意見・「出生時3」";
        else if ("Grid10.h1.w1".equals(code)) obj = "２. 特別な医療・「処置内容」　見出し";
        else if ("Grid10.h3.w1".equals(code)) obj = "２. 特別な医療・「特別な対応」　見出し";
        else if ("Grid10.h4.w1".equals(code)) obj = "２. 特別な医療・「失禁への対応」　見出し";
        else if ("Grid1.h1.w2".equals(code)) obj = "申請者情報・「ふりがな」　見出し";
        else if ("Grid1.h1.w3".equals(code)) obj = "申請者情報・「ふりがな」";
        else if ("Grid1.h1.w12".equals(code)) obj = "申請者情報・「〒」　見出し";
        else if ("Grid1.h1.w11".equals(code)) obj = "申請者情報・「郵便番号1」";
        else if ("Grid1.h1.w17".equals(code)) obj = "申請者情報・「郵便番号2」";
        else if ("Grid1.h2.w1".equals(code)) obj = "申請者情報・「 申 請 者」　見出し";
        else if ("Grid1.h2.w3".equals(code)) obj = "申請者情報・「患者名」";
        else if ("Grid1.h2.w12".equals(code)) obj = "申請者情報・「住所」";
        else if ("Grid1.h3.w3".equals(code)) obj = "申請者情報・生年月日「年」";
        else if ("Grid1.h3.w4".equals(code)) obj = "申請者情報・生年月日「年」　見出し";
        else if ("Grid1.h3.w5".equals(code)) obj = "申請者情報・生年月日「月」";
        else if ("Grid1.h3.w6".equals(code)) obj = "申請者情報・生年月日「月」　見出し";
        else if ("Grid1.h3.w7".equals(code)) obj = "申請者情報・生年月日「日」";
        else if ("Grid1.h3.w8".equals(code)) obj = "申請者情報・生年月日「日生」　見出し";
        else if ("Grid1.h3.w10".equals(code)) obj = "申請者情報・生年月日「年齢」　見出し";
        else if ("Grid1.h3.w11".equals(code)) obj = "申請者情報・「連絡先」　見出し";
        else if ("Grid1.h3.w17".equals(code)) obj = "申請者情報・「電話番号1」";
        else if ("Grid1.h3.w19".equals(code)) obj = "申請者情報・「電話番号2」";
        else if ("Grid1.h3.w21".equals(code)) obj = "申請者情報・「電話番号3」";
        else if ("Grid4.h1.w1".equals(code)) obj = "「医師氏名」　見出し";
        else if ("Grid4.h1.w3".equals(code)) obj = "「医師氏名」";
        else if ("Grid4.h2.w1".equals(code)) obj = "「医療機関名」　見出し";
        else if ("Grid4.h2.w4".equals(code)) obj = "「医療機関名」";
        else if ("Grid4.h3.w1".equals(code)) obj = "「医療機関所在地」　見出し";
        else if ("Grid4.h3.w5".equals(code)) obj = "「医療機関所在地」";
        else if ("Grid14.h3.w9".equals(code)) obj = "３.心身の状態に関する意見・(１)行動上の障害の有無「その他」　内容";
        else if ("Grid7.h1.w2".equals(code)) obj = "１. 傷病に関する意見・「(１) 診断名」　見出し";
        else if ("Grid7.h2.w2".equals(code)) obj = "１. 傷病に関する意見・「(２) 症状としての安定性」　見出し";
        else if ("Grid7.h5.w2".equals(code)) obj = "１. 傷病に関する意見・「(３) 障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容」　見出し";
        else if ("Grid8.h1.w2".equals(code)) obj = "１. 傷病に関する意見・「診断名1」";
        else if ("Grid8.h1.w3".equals(code)) obj = "１. 傷病に関する意見・「発症年月日1」　見出し";
        else if ("Grid8.h1.w15".equals(code)) obj = "１. 傷病に関する意見・「発症年月日1-1」";
        else if ("Grid8.h1.w14".equals(code)) obj = "１. 傷病に関する意見・「発症年月日1-2」";
        else if ("Grid8.h1.w13".equals(code)) obj = "１. 傷病に関する意見・発症年月日「年」　見出し";
        else if ("Grid8.h1.w12".equals(code)) obj = "１. 傷病に関する意見・「発症年月日1-3」";
        else if ("Grid8.h1.w11".equals(code)) obj = "１. 傷病に関する意見・発症年月日「月」　見出し";
        else if ("Grid8.h1.w10".equals(code)) obj = "１. 傷病に関する意見・「発症年月日1-4」";
        else if ("Grid8.h1.w9".equals(code)) obj = "１. 傷病に関する意見・発症年月日「日頃」　見出し";
        else if ("Grid8.h2.w2".equals(code)) obj = "１. 傷病に関する意見・「診断名2」";
        else if ("Grid8.h2.w3".equals(code)) obj = "１. 傷病に関する意見・「発症年月日2」　見出し";
        else if ("Grid8.h2.w15".equals(code)) obj = "１. 傷病に関する意見・「発症年月日2-1」";
        else if ("Grid8.h2.w14".equals(code)) obj = "１. 傷病に関する意見・「発症年月日2-2」";
        else if ("Grid8.h2.w13".equals(code)) obj = "１. 傷病に関する意見・発症年月日「年」　見出し";
        else if ("Grid8.h2.w12".equals(code)) obj = "１. 傷病に関する意見・「発症年月日2-3」";
        else if ("Grid8.h2.w11".equals(code)) obj = "１. 傷病に関する意見・発症年月日「月」　見出し";
        else if ("Grid8.h2.w10".equals(code)) obj = "１. 傷病に関する意見・「発症年月日2-4」";
        else if ("Grid8.h2.w9".equals(code)) obj = "１. 傷病に関する意見・発症年月日「日頃」　見出し";
        else if ("Grid8.h3.w2".equals(code)) obj = "１. 傷病に関する意見・「診断名3」";
        else if ("Grid8.h3.w3".equals(code)) obj = "１. 傷病に関する意見・「発症年月日3」　見出し";
        else if ("Grid8.h3.w15".equals(code)) obj = "１. 傷病に関する意見・「発症年月日3-1」";
        else if ("Grid8.h3.w14".equals(code)) obj = "１. 傷病に関する意見・「発症年月日3-2」";
        else if ("Grid8.h3.w13".equals(code)) obj = "１. 傷病に関する意見・発症年月日「年」　見出し";
        else if ("Grid8.h3.w12".equals(code)) obj = "１. 傷病に関する意見・「発症年月日3-3」";
        else if ("Grid8.h3.w11".equals(code)) obj = "１. 傷病に関する意見・発症年月日「月」　見出し";
        else if ("Grid8.h3.w10".equals(code)) obj = "１. 傷病に関する意見・「発症年月日3-4」";
        else if ("Grid8.h3.w9".equals(code)) obj = "１. 傷病に関する意見・発症年月日「日頃」　見出し";
        else if ("Grid13.h1.w1".equals(code)) obj = "３. 心身の状態に関する意見・「(２) 精神･神経症状の有無」　見出し";
        else if ("Grid6.h1.w1".equals(code)) obj = "「(１) 最終診察日」　見出し";
        else if ("Grid6.h1.w12".equals(code)) obj = "(１) 最終診察日「元号」";
        else if ("Grid6.h1.w11".equals(code)) obj = "(１) 最終診察日「年」";
        else if ("Grid6.h1.w10".equals(code)) obj = "(１) 最終診察日「年」　見出し";
        else if ("Grid6.h1.w9".equals(code)) obj = "(１) 最終診察日「月」";
        else if ("Grid6.h1.w8".equals(code)) obj = "(１) 最終診察日「月」　見出し";
        else if ("Grid6.h1.w7".equals(code)) obj = "(１) 最終診察日「日」";
        else if ("Grid6.h1.w6".equals(code)) obj = "(１) 最終診察日「日」　見出し";
        else if ("Grid6.h2.w1".equals(code)) obj = "「(２) 意見書作成回数」　見出し";
        else if ("Grid6.h5.w1".equals(code)) obj = "「(３) 他科受診の有無」　見出し";
        else if ("Grid6.h3.w4".equals(code)) obj = "(３) 他科受診の有無・「その他」　内容";
        else if ("Grid5.h1.w1".equals(code)) obj = "「医療機関連絡先」　見出し";
        else if ("Grid5.h1.w2".equals(code)) obj = "「医療機関連絡先1」";
        else if ("Grid5.h1.w4".equals(code)) obj = "「医療機関連絡先2」";
        else if ("Grid5.h1.w6".equals(code)) obj = "「医療機関連絡先3」";
        else if ("Grid5.h3.w1".equals(code)) obj = "「医療機関FAX」　見出し";
        else if ("Grid5.h3.w2".equals(code)) obj = "「医療機関FAX1」";
        else if ("Grid5.h3.w4".equals(code)) obj = "「医療機関FAX2」";
        else if ("Grid5.h3.w6".equals(code)) obj = "「医療機関FAX3」";
        else if ("Grid9.h1.w1".equals(code)) obj = "疾病の経過・治療内容・投薬内容1";
        else if ("Grid9.h1.w2".equals(code)) obj = "疾病の経過・治療内容・投薬内容2";
        else if ("Grid9.h2.w1".equals(code)) obj = "疾病の経過・治療内容・投薬内容3";
        else if ("Grid9.h2.w2".equals(code)) obj = "疾病の経過・治療内容・投薬内容4";
        else if ("Grid9.h3.w1".equals(code)) obj = "疾病の経過・治療内容・投薬内容5";
        else if ("Grid9.h3.w2".equals(code)) obj = "疾病の経過・治療内容・投薬内容6";
        else if ("Grid11.h1.w1".equals(code)) obj = "１. 傷病に関する意見・「1」　見出し";
        else if ("Grid11.h1.w2".equals(code)) obj = "１. 傷病に関する意見・「入院歴開始1-1」";
        else if ("Grid11.h1.w3".equals(code)) obj = "１. 傷病に関する意見・「入院歴開始1-2」";
        else if ("Grid11.h1.w5".equals(code)) obj = "１. 傷病に関する意見・「入院歴開始1-3」";
        else if ("Grid11.h1.w7".equals(code)) obj = "１. 傷病に関する意見・「入院歴終了1-1」";
        else if ("Grid11.h1.w8".equals(code)) obj = "１. 傷病に関する意見・「入院歴終了1-2」";
        else if ("Grid11.h1.w10".equals(code)) obj = "１. 傷病に関する意見・「入院歴終了1-3」";
        else if ("Grid11.h1.w13".equals(code)) obj = "１. 傷病に関する意見・「入院歴終了1傷病名」";
        else if ("Grid12.h1.w1".equals(code)) obj = "１. 傷病に関する意見・「2」　見出し";
        else if ("Grid12.h1.w2".equals(code)) obj = "１. 傷病に関する意見・「入院歴開始2-1」";
        else if ("Grid12.h1.w3".equals(code)) obj = "１. 傷病に関する意見・「入院歴開始2-2」";
        else if ("Grid12.h1.w5".equals(code)) obj = "１. 傷病に関する意見・「入院歴開始2-3」";
        else if ("Grid12.h1.w7".equals(code)) obj = "１. 傷病に関する意見・「入院歴終了2-1」";
        else if ("Grid12.h1.w8".equals(code)) obj = "１. 傷病に関する意見・「入院歴終了2-2」";
        else if ("Grid12.h1.w10".equals(code)) obj = "１. 傷病に関する意見・「入院歴終了2-3」";
        else if ("Grid12.h1.w13".equals(code)) obj = "１. 傷病に関する意見・「入院歴終了2傷病名」";
        else if ("Grid2.h1.w1".equals(code)) obj = "「記入日」　元号　見出し";
        else if ("Grid2.h1.w2".equals(code)) obj = "「記入日」　元号";
        else if ("Grid2.h1.w3".equals(code)) obj = "「記入日」　年";
        else if ("Grid2.h1.w4".equals(code)) obj = "「記入日」　年　見出し";
        else if ("Grid2.h1.w5".equals(code)) obj = "「記入日」　月";
        else if ("Grid2.h1.w6".equals(code)) obj = "「記入日」　月　見出し";
        else if ("Grid2.h1.w7".equals(code)) obj = "「記入日」　日";
        else if ("Grid2.h1.w8".equals(code)) obj = "「記入日」　日　見出し";
        return obj;
    }

    /**
     * 医師意見書2ページ目の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatIkenshoShien2(String code, Object obj){
        // 医師意見書2ページ目
        if ("NijikuSeishin".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「二軸評価：精神症状」";
        else if ("INSURER_NO".equals(code)) obj = "保険者番号";
        // [ID:0000555][Tozo TANAKA] 2009/09/14 replace begin 【2009年度対応：追加案件】医師意見書の受給者番号対応
//        else if ("INSURED_NO".equals(code)) obj = "被保険者番号";
        else if ("INSURED_NO".equals(code)) obj = "受給者番号";
        // [ID:0000555][Tozo TANAKA] 2009/09/14 replace end 【2009年度対応：追加案件】医師意見書の受給者番号対応
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add begin 【2009年度対応：追加案件】医師意見書の受給者番号対応
        else if ("INSURER_NO_LABEL".equals(code)) obj = "保険者番号 見出し";
        else if ("INSURERD_NO_LABEL".equals(code)) obj = "受給者番号 見出し";
        else if ("FD_OUTPUT_TIME_LABEL".equals(code)) obj = "作成日時 見出し";
        else if ("Label113".equals(code)) obj = "在宅・施設区分";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add end 【2009年度対応：追加案件】医師意見書の受給者番号対応
        else if ("Label7".equals(code)) obj = "「４. サービス利用に関する意見」　見出し";
        else if ("Label9".equals(code)) obj = "「５. その他特記すべき事項」　見出し";
        else if ("KansetsuItamiBui".equals(code)) obj = "３.心身の状態に関する意見・関節の痛み「部位」　部位";
        else if ("IkenByoutaitaName".equals(code)) obj = "４. サービス利用に関する意見・(１)現在、発生の可能性が高い病態とその対処方針「その他」";
        else if ("MahiOtherBui".equals(code)) obj = "３.心身の状態に関する意見・麻痺「その他」　部位";
        else if ("KoushukuOtherBui".equals(code)) obj = "３.心身の状態に関する意見・関節の拘縮「その他部位」";
        else if ("JokusouBui".equals(code)) obj = "３.心身の状態に関する意見・褥瘡「部位」";
        else if ("Label178".equals(code)) obj = "３.心身の状態に関する意見・その他の皮膚疾患「部位」　見出し";
        else if ("HifuShikkanBui".equals(code)) obj = "３.心身の状態に関する意見・その他の皮膚疾患「部位」";
        else if ("KinouHyoukaTitle".equals(code)) obj = "５. その他特記すべき事項・「<精神障害の機能評価>」　見出し";
        else if ("NijikuNoryoku".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「二軸評価：能力障害」";
        else if ("SeikatsuShokuji".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「生活障害評価：食事」";
        else if ("SeikatsuRhythm".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「生活障害評価：生活リズム」";
        else if ("SeikatsuHosei".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「生活障害評価：保清」";
        else if ("SeikatsuKinsenKanri".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「生活障害評価：金銭管理」";
        else if ("SeikatsuFukuyakuKanri".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「生活障害評価：服薬管理」";
        else if ("SeikatsuTaijinKankei".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「生活障害評価：対人関係」";
        else if ("SeikatsuShakaiTekiou".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「生活障害評価：社会的適応を妨げる行動」";
        else if ("SeikatsuHanteiYear".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「生活障害評価：判断時期」　年";
        else if ("SeikatsuHanteiMonth".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「生活障害評価：判断時期」　月";
        else if ("NijikuHanteiYear".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「二軸評価：判定時期」　年";
        else if ("NijikuHanteiMonth".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「二軸評価：判定時期」　月";
        else if ("IkenKaigoOther".equals(code)) obj = "４. サービス利用に関する意見・(２)介護サービス（ホームヘルプサービス等）の利用時に関する医学的観点からの留意事項「その他」";
        else if ("KinryokuTeikaBui".equals(code)) obj = "３.心身の状態に関する意見・筋力の低下「部位」";
        else if ("NijikuHanteiEra".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「二軸評価：判定時期」　元号";
        else if ("SeikatsuHanteiEra".equals(code)) obj = "５. その他特記すべき事項・<精神障害の機能評価>「生活障害評価：判断時期」　元号";
        else if ("ShishikessonBui".equals(code)) obj = "３.心身の状態に関する意見・四肢欠損「部位」";
        else if ("Grid3.h1.w1".equals(code)) obj = "３.心身の状態に関する意見・「(３)身体の状態」　見出し";
        else if ("Grid15.h1.w1".equals(code)) obj = "４. サービス利用に関する意見・「(３)感染症の有無 (有の場合は具体的に記入して下さい)」　見出し";
        else if ("Grid16.h1.w5".equals(code)) obj = "４. サービス利用に関する意見・「(３)感染症の有無」　詳細";
        else if ("Grid8.h1.w1".equals(code)) obj = "４. サービス利用に関する意見・「(１)現在、発生の可能性が高い病態とその対処方針」　見出し";
        else if ("Grid10.h1.w2".equals(code)) obj = "４. サービス利用に関する意見・「対処方針」";
        else if ("Grid23.h1.w1".equals(code)) obj = "３.心身の状態に関する意見・「＜てんかん＞」　見出し";
        else if ("Grid4.h1.w1".equals(code)) obj = "３.心身の状態に関する意見・「利き腕（」　見出し";
        else if ("Grid4.h1.w2".equals(code)) obj = "３.心身の状態に関する意見・「身長」";
        else if ("Grid4.h1.w3".equals(code)) obj = "３.心身の状態に関する意見・「体重」　見出し";
        else if ("Grid4.h1.w4".equals(code)) obj = "３.心身の状態に関する意見・「体重」";
        else if ("Grid13.h1.w1".equals(code)) obj = "４. サービス利用に関する意見・「(２)介護サービス（ホームヘルプサービス等）の利用時に関する医学的観点からの留意事項」　見出し";
        else if ("Grid2.h1.w1".equals(code)) obj = "申請者情報・「患者氏名」";
        else if ("Grid2.h1.w3".equals(code)) obj = "申請者情報・「年齢」";
        else if ("Grid2.h1.w4".equals(code)) obj = "申請者情報・「年齢」　見出し";
        else if ("Grid2.h1.w5".equals(code)) obj = "申請者情報・生年月日「元号」";
        else if ("Grid2.h1.w6".equals(code)) obj = "申請者情報・生年月日「年」";
        else if ("Grid2.h1.w7".equals(code)) obj = "申請者情報・生年月日「年」　見出し";
        else if ("Grid2.h1.w8".equals(code)) obj = "申請者情報・生年月日「月」";
        else if ("Grid2.h1.w9".equals(code)) obj = "申請者情報・生年月日「月」　見出し";
        else if ("Grid2.h1.w10".equals(code)) obj = "申請者情報・生年月日「日」";
        else if ("Grid2.h1.w11".equals(code)) obj = "申請者情報・生年月日「日」　見出し";
        return obj;
    }

    //  [ID:0000514][Tozo TANAKA] 2009/09/09 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
    /**
     * 特別訪問看護指示書の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatTokubetsuShijisho(String code, Object obj){
        if ("TENTEKI".equals(code)) obj = "点滴注射指示内容";
        else if ("Grid3".equals(code)) obj = "点滴注射指示内容　枠";
        else if ("Grid3.h2.w1".equals(code)) obj = "点滴注射指示内容　見出し";
        else if ("SyojoSyusoGrid".equals(code)) obj = "症状・主訴　枠";
        else if ("SyojoSyusoGrid.h2.w1".equals(code)) obj = "症状・主訴　見出し";
        else if ("SYOJYO".equals(code)) obj = "症状・主訴";
        else if ("RyuiShijiGrid".equals(code)) obj = "留意事項及び指示事項　枠";
        else if ("Label17".equals(code)) obj = "留意事項及び指示事項　見出し";
//      [ID:0000558][Tozo TANAKA] 2009/10/13 replace begin 【障害】特別指示書の帳票印字文言に誤字  
//        else if ("Label18".equals(code)) obj = "（注：点滴注射薬の相互作用・福作用についての留意点があれば記載してください。）　見出し";
        else if ("Label18".equals(code)) obj = "（注：点滴注射薬の相互作用・副作用についての留意点があれば記載してください。）　見出し";
//      [ID:0000558][Tozo TANAKA] 2009/10/13 replace end 【障害】特別指示書の帳票印字文言に誤字  
        else if ("RYUI".equals(code)) obj = "留意事項及び指示事項";
        else if ("Label10".equals(code)) obj = "生年月日・元号　「平」　見出し";
        else if ("Label8".equals(code)) obj = "生年月日・元号　「大」　見出し";
        else if ("Label7".equals(code)) obj = "生年月日・元号　「明｣　見出し";
        else if ("Shape27".equals(code)) obj = "日常生活自立度・寝たきり度　「自立　／」";
        else if ("Label9".equals(code)) obj = "生年月日・元号　「昭」　見出し";
        else if ("Shape1".equals(code)) obj = "生年月日・元号　「明」　○";
        else if ("Shape2".equals(code)) obj = "生年月日・元号　「大」　○";
        else if ("Shape3".equals(code)) obj = "生年月日・元号　「昭」　○";
        else if ("Shape4".equals(code)) obj = "生年月日・元号　「平」　○";
        else if ("Label1".equals(code)) obj = "「上記のとおり、指定訪問看護の実施を指示いたします。」　見出し";
        else if ("KinyuDateLabell".equals(code)) obj = "作成年月日　「平成  年  月  日」";
        else if ("Label3".equals(code)) obj = "訪問看護ステーション　「印」　見出し";
        else if ("IryoKikanLabel".equals(code)) obj = "「医療機関名」　見出し";
        else if ("Label5".equals(code)) obj = "「医療機関医師氏名」　見出し";
        else if ("StationNameLabel".equals(code)) obj = "訪問看護ステーション名";
        else if ("IryoKikanGrid.h1.w2".equals(code)) obj = "医療機関名";
        else if ("IryoKikanGrid.h2.w1".equals(code)) obj = "「住　　　所」　見出し";
        else if ("IryoKikanGrid.h2.w2".equals(code)) obj = "医療機関住所";
        else if ("IryoKikanGrid.h3.w1".equals(code)) obj = "「電　　　話」　見出し";
        else if ("IryoKikanGrid.h3.w2".equals(code)) obj = "医療機関　電話番号";
        else if ("IryoKikanGrid.h4.w1".equals(code)) obj = "「（ＦＡＸ）」　見出し";
        else if ("IryoKikanGrid.h4.w2".equals(code)) obj = "医療機関　ＦＡＸ番号";
        else if ("IryoKikanGrid.h5.w2".equals(code)) obj = "医療機関医師氏名";
        else if ("SijiDateGrid".equals(code)) obj = "訪問看護指示期間・点滴注射指示期間　枠";
        else if ("SijiDateGrid.h1.w14".equals(code)) obj = "「訪問看護指示期間」　見出し";
        else if ("SijiDateGrid.h1.w2".equals(code)) obj = "訪問看護指示期間　開始年";
        else if ("SijiDateGrid.h1.w3".equals(code)) obj = "訪問看護指示期間　開始年　見出し";
        else if ("SijiDateGrid.h1.w4".equals(code)) obj = "訪問看護指示期間　開始月";
        else if ("SijiDateGrid.h1.w5".equals(code)) obj = "訪問看護指示期間　開始月　見出し";
        else if ("SijiDateGrid.h1.w6".equals(code)) obj = "訪問看護指示期間　開始日";
        else if ("SijiDateGrid.h1.w7".equals(code)) obj = "訪問看護指示期間　開始日　見出し";
        else if ("SijiDateGrid.h1.w8".equals(code)) obj = "訪問看護指示期間　終了年";
        else if ("SijiDateGrid.h1.w9".equals(code)) obj = "訪問看護指示期間　終了年　見出し";
        else if ("SijiDateGrid.h1.w10".equals(code)) obj = "訪問看護指示期間　終了月";
        else if ("SijiDateGrid.h1.w11".equals(code)) obj = "訪問看護指示期間　終了月　見出し";
        else if ("SijiDateGrid.h1.w12".equals(code)) obj = "訪問看護指示期間　終了日";
        else if ("SijiDateGrid.h1.w13".equals(code)) obj = "訪問看護指示期間　終了日　見出し";
        else if ("SijiDateGrid.h2.w14".equals(code)) obj = "「点滴注射指示期間」　見出し";
        else if ("SijiDateGrid.h2.w2".equals(code)) obj = "点滴注射指示期間　開始年";
        else if ("SijiDateGrid.h2.w3".equals(code)) obj = "点滴注射指示期間　開始年　見出し";
        else if ("SijiDateGrid.h2.w4".equals(code)) obj = "点滴注射指示期間　開始月";
        else if ("SijiDateGrid.h2.w5".equals(code)) obj = "点滴注射指示期間　開始月　見出し";
        else if ("SijiDateGrid.h2.w6".equals(code)) obj = "点滴注射指示期間　開始日";
        else if ("SijiDateGrid.h2.w7".equals(code)) obj = "点滴注射指示期間　開始日　見出し";
        else if ("SijiDateGrid.h2.w8".equals(code)) obj = "点滴注射指示期間　終了年";
        else if ("SijiDateGrid.h2.w9".equals(code)) obj = "点滴注射指示期間　終了年　見出し";
        else if ("SijiDateGrid.h2.w10".equals(code)) obj = "点滴注射指示期間　終了月";
        else if ("SijiDateGrid.h2.w11".equals(code)) obj = "点滴注射指示期間　終了月　見出し";
        else if ("SijiDateGrid.h2.w12".equals(code)) obj = "点滴注射指示期間　終了日";
        else if ("SijiDateGrid.h2.w13".equals(code)) obj = "点滴注射指示期間　終了日　見出し";
        else if ("KanjyaGrid.h1.w1".equals(code)) obj = "「患者氏名」　見出し";
        else if ("KanjyaGrid.h1.w2".equals(code)) obj = "患者氏名";
        else if ("KanjyaGrid.h1.w3".equals(code)) obj = "「生年月日」";
        else if ("KanjyaGrid.h1.w5".equals(code)) obj = "生年月日・年";
        else if ("KanjyaGrid.h1.w6".equals(code)) obj = "生年月日・年　見出し";
        else if ("KanjyaGrid.h1.w7".equals(code)) obj = "生年月日・月";
        else if ("KanjyaGrid.h1.w8".equals(code)) obj = "生年月日・月　見出し";
        else if ("KanjyaGrid.h1.w9".equals(code)) obj = "生年月日・日";
        else if ("KanjyaGrid.h1.w10".equals(code)) obj = "生年月日・日　見出し";
        else if ("KanjyaGrid.h1.w12".equals(code)) obj = "歳）";
        else if ("Grid4".equals(code)) obj = "「緊急時の連絡先」　枠";
        else if ("Grid4.h2.w1".equals(code)) obj = "「緊急時の連絡先」　見出し";
        else if ("KINKYU".equals(code)) obj = "緊急時の連絡先";
        else if ("TITLE_TOKUBETU".equals(code)) obj = "特別訪問看護指示書　見出し";
        else if ("TITLE_TENTEKI".equals(code)) obj = "在宅患者訪問点滴注射指示書　見出し";
        else if ("TITLE_TOKUBETU_TENTEKI".equals(code)) obj = "特別訪問看護指示書・在宅患者訪問点滴注射指示書　見出し";
        return obj;
    }
    //  [ID:0000514][Tozo TANAKA] 2009/09/09 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能
    
    
    //[ID:0000639][Shin Fujihara] 2011/03 add begin
    /**
     * 訪問看護指示書（医療機関）1ページ目の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatShijisho_M1(String code, Object obj){
    	
    	if ("title".equals(code)) obj = "「訪問看護指示書」　見出し";
    	
        else if ("Grid1".equals(code)) obj = "訪問看護指示期間・点滴注射指示期間　枠";
        else if ("Grid1.h1.w14".equals(code)) obj = "「訪問看護指示期間」　見出し";
        else if ("Grid1.h1.w2".equals(code)) obj = "訪問看護指示期間　開始年";
        else if ("Grid1.h1.w3".equals(code)) obj = "訪問看護指示期間　開始年　見出し";
        else if ("Grid1.h1.w4".equals(code)) obj = "訪問看護指示期間　開始月";
        else if ("Grid1.h1.w5".equals(code)) obj = "訪問看護指示期間　開始月　見出し";
        else if ("Grid1.h1.w6".equals(code)) obj = "訪問看護指示期間　開始日";
        else if ("Grid1.h1.w7".equals(code)) obj = "訪問看護指示期間　開始日　見出し";
        else if ("Grid1.h1.w8".equals(code)) obj = "訪問看護指示期間　終了年";
        else if ("Grid1.h1.w9".equals(code)) obj = "訪問看護指示期間　終了年　見出し";
        else if ("Grid1.h1.w10".equals(code)) obj = "訪問看護指示期間　終了月";
        else if ("Grid1.h1.w11".equals(code)) obj = "訪問看護指示期間　終了月　見出し";
        else if ("Grid1.h1.w12".equals(code)) obj = "訪問看護指示期間　終了日";
        else if ("Grid1.h1.w13".equals(code)) obj = "訪問看護指示期間　終了日　見出し";
        else if ("Grid1.h2.w14".equals(code)) obj = "「点滴注射指示期間」　見出し";
        else if ("Grid1.h2.w2".equals(code)) obj = "点滴注射指示期間　開始年";
        else if ("Grid1.h2.w3".equals(code)) obj = "点滴注射指示期間　開始年　見出し";
        else if ("Grid1.h2.w4".equals(code)) obj = "点滴注射指示期間　開始月";
        else if ("Grid1.h2.w5".equals(code)) obj = "点滴注射指示期間　開始月　見出し";
        else if ("Grid1.h2.w6".equals(code)) obj = "点滴注射指示期間　開始日";
        else if ("Grid1.h2.w7".equals(code)) obj = "点滴注射指示期間　開始日　見出し";
        else if ("Grid1.h2.w8".equals(code)) obj = "点滴注射指示期間　終了年";
        else if ("Grid1.h2.w9".equals(code)) obj = "点滴注射指示期間　終了年　見出し";
        else if ("Grid1.h2.w10".equals(code)) obj = "点滴注射指示期間　終了月";
        else if ("Grid1.h2.w11".equals(code)) obj = "点滴注射指示期間　終了月　見出し";
        else if ("Grid1.h2.w12".equals(code)) obj = "点滴注射指示期間　終了日";
        else if ("Grid1.h2.w13".equals(code)) obj = "点滴注射指示期間　終了日　見出し";
    	
        else if ("Grid2.h1.w1".equals(code)) obj = "「患者氏名」　見出し";
        else if ("Grid2.h1.w2".equals(code)) obj = "患者氏名";
        else if ("Grid2.h1.w3".equals(code)) obj = "「生年月日」";
        else if ("Grid2.h1.w5".equals(code)) obj = "生年月日・年";
        else if ("Grid2.h1.w6".equals(code)) obj = "生年月日・年　見出し";
        else if ("Grid2.h1.w7".equals(code)) obj = "生年月日・月";
        else if ("Grid2.h1.w8".equals(code)) obj = "生年月日・月　見出し";
        else if ("Grid2.h1.w9".equals(code)) obj = "生年月日・日";
        else if ("Grid2.h1.w10".equals(code)) obj = "生年月日・日　見出し";
        else if ("Grid2.h1.w12".equals(code)) obj = "歳）";
        
        else if ("Label7".equals(code)) obj = "生年月日・元号　「明｣　見出し";
        else if ("Label8".equals(code)) obj = "生年月日・元号　「大」　見出し";
        else if ("Label9".equals(code)) obj = "生年月日・元号　「昭」　見出し";
        else if ("Label10".equals(code)) obj = "生年月日・元号　「平」　見出し";
    	
    	
        else if ("Grid3.h1.w1".equals(code)) obj = "「患者住所」　見出し";
        else if ("Grid3.h1.w3".equals(code)) obj = "「〒」　見出し";
        else if ("Grid3.h1.w4".equals(code)) obj = "患者郵便番号";
        else if ("Grid3.h2.w4".equals(code)) obj = "患者住所";
        else if ("Grid3.h3.w2".equals(code)) obj = "電話番号";
        else if ("Grid3.h3.w6".equals(code)) obj = "「電話」　見出し";

        else if ("Grid4.h1.w1".equals(code)) obj = "「主たる傷病名」　見出し";
        else if ("Grid4.h1.w2".equals(code)) obj = "主たる傷病名";
    	
        else if ("Grid5.h1.w1".equals(code)) obj = "「現在の状況」　見出し";
    	
        else if ("Grid6.h1.w1".equals(code)) obj = "「症状・治療状態」　見出し";
        else if ("lblJyotai".equals(code)) obj = "症状・治療状態";

        else if ("sickMedicines8.h1.w1".equals(code)) obj = "投与中の薬剤の用法・用量・「投与中の薬剤の用法・用量」　見出し";
        else if ("sickMedicines8.h1.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「１」　見出し";
        else if ("sickMedicines8.h1.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「１」";
        else if ("sickMedicines8.h1.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「２」　見出し";
        else if ("sickMedicines8.h1.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「２」";
        else if ("sickMedicines8.h2.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「３」　見出し";
        else if ("sickMedicines8.h2.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「３」";
        else if ("sickMedicines8.h2.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「４」　見出し";
        else if ("sickMedicines8.h2.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「４」";
        else if ("sickMedicines8.h3.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「５」　見出し";
        else if ("sickMedicines8.h3.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「５」";
        else if ("sickMedicines8.h3.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「６」　見出し";
        else if ("sickMedicines8.h3.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「６」";
        else if ("sickMedicines8.h4.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「７」　見出し";
        else if ("sickMedicines8.h4.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「７」";
        else if ("sickMedicines8.h4.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「８」　見出し";
        else if ("sickMedicines8.h4.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「８」";
    	
        else if ("Grid8".equals(code)) obj = "日常生活　枠";
        else if ("Grid8.h1.w1".equals(code)) obj = "日常生活自立度　「日常生活」　見出し";
        else if ("Grid8.h1.w2".equals(code)) obj = "「寝たきり度」　見出し";
        else if ("Grid8.h1.w19".equals(code)) obj = " J1";
        else if ("Grid8.h1.w17".equals(code)) obj = " J2";
        else if ("Grid8.h1.w15".equals(code)) obj = " A1";
        else if ("Grid8.h1.w13".equals(code)) obj = " A2";
        else if ("Grid8.h1.w11".equals(code)) obj = " B1";
        else if ("Grid8.h1.w9".equals(code)) obj = " B2";
        else if ("Grid8.h1.w7".equals(code)) obj = " C1";
        else if ("Grid8.h1.w5".equals(code)) obj = " C2";
        else if ("Grid8.h2.w1".equals(code)) obj = "日常生活自立度　「自 立 度」　見出し";
        else if ("Grid8.h2.w2".equals(code)) obj = "「認知症の状況」　見出し";
        else if ("Grid8.h2.w19".equals(code)) obj = " I";
        else if ("Grid8.h2.w17".equals(code)) obj = " IIａ";
        else if ("Grid8.h2.w15".equals(code)) obj = " IIｂ";
        else if ("Grid8.h2.w13".equals(code)) obj = " IIIａ";
        else if ("Grid8.h2.w11".equals(code)) obj = " IIIｂ";
        else if ("Grid8.h2.w9".equals(code)) obj = " IV";
        else if ("Grid8.h2.w7".equals(code)) obj = " Ｍ";

        else if ("Label23".equals(code)) obj = "「要介護認定の状況」　見出し";
        else if ("Grid8.h3.w21".equals(code)) obj = "要支援";
        else if ("Grid8.h3.w19".equals(code)) obj = "要介護";
    	
        else if ("Label18".equals(code)) obj = "要介護認定の状況　「５ ）」";
        else if ("Label17".equals(code)) obj = "要介護認定の状況　「４」";
        else if ("Label16".equals(code)) obj = "要介護認定の状況　「３」";
        else if ("Label15".equals(code)) obj = "要介護認定の状況　「２」";
        else if ("Label14".equals(code)) obj = "要介護認定の状況　「１」";
    	
        else if ("Label26".equals(code)) obj = "「褥瘡の深さ」　見出し";
        else if ("Grid15.h3.w22".equals(code)) obj = "褥瘡の深さ　「NPUAP分類」";
        else if ("Grid15.h3.w20".equals(code)) obj = "褥瘡の深さ・NPUAP分類　「Ⅲ度」";
        else if ("Grid15.h3.w17".equals(code)) obj = "褥瘡の深さ・NPUAP分類　「Ⅳ度」";
        else if ("Grid15.h3.w15".equals(code)) obj = "褥瘡の深さ　「DESIGN分類」";
        else if ("Grid15.h3.w13".equals(code)) obj = "褥瘡の深さ・DESIGN分類　「D3」";
        else if ("Grid15.h3.w11".equals(code)) obj = "褥瘡の深さ・DESIGN分類　「D4」";
        else if ("Grid15.h3.w9".equals(code)) obj = "褥瘡の深さ・DESIGN分類　「D5」";
    	
        else if ("Grid9".equals(code)) obj = "「装着・使用医療機器等」　枠";
        else if ("Grid9.h1.w1".equals(code)) obj = "「装着・使用医療機器等」　見出し";
        else if ("Grid9.h1.w3".equals(code)) obj = "１．自動腹膜灌流装置・「1」　見出し";
        else if ("Grid9.h1.w4".equals(code)) obj = "１．自動腹膜灌流装置・「自動腹膜灌流装置」　見出し";
        else if ("Grid9.h1.w7".equals(code)) obj = "２．透析液供給装置・「2」　見出し";
        else if ("Grid9.h1.w8".equals(code)) obj = "２．透析液供給装置・「透析液供給装置」　見出し";
        else if ("Grid9.h1.w15".equals(code)) obj = "３．酸素療法・「3」　見出し";
        else if ("Grid9.h1.w24".equals(code)) obj = "３．酸素療法・「酸素療法（」　見出し";
        else if ("Grid9.h1.w20".equals(code)) obj = "３．酸素療法";
        else if ("Grid9.h1.w21".equals(code)) obj = "３．酸素療法・「 l/min」　見出し";
        else if ("Grid9.h1.w22".equals(code)) obj = "３．酸素療法・「）」　見出し";
        
        else if ("Grid9.h2.w3".equals(code)) obj = "４．吸引器・「4」　見出し";
        else if ("Grid9.h2.w4".equals(code)) obj = "４．吸引器・「吸引器」　見出し";
        else if ("Grid9.h2.w7".equals(code)) obj = "５．中心静脈栄養・「5」　見出し";
        else if ("Grid9.h2.w8".equals(code)) obj = "５．中心静脈栄養・「中心静脈栄養」　見出し";
        else if ("Grid9.h2.w15".equals(code)) obj = "６．輸液ポンプ・「6」　見出し";
        else if ("Grid9.h2.w24".equals(code)) obj = "６．輸液ポンプ・「輸液ポンプ」　見出し";
        
        else if ("Grid9.h3.w3".equals(code)) obj = "７．経管栄養・「7」　見出し";
        else if ("Grid9.h3.w4".equals(code)) obj = "７．経管栄養・「経管栄養　　　（」　見出し";
        else if ("Grid9.h3.w23".equals(code)) obj = "７．経管栄養";
        else if ("Grid9.h3.w9".equals(code)) obj = "７．経管栄養・「：チューブサイズ」　見出し";
        else if ("Grid9.h3.w15".equals(code)) obj = "７．経管栄養・「：チューブサイズ」";
        else if ("Grid9.h3.w16".equals(code)) obj = "７．経管栄養・「、」　見出し";
        else if ("Grid9.h3.w18".equals(code)) obj = "７．経管栄養　日";
        else if ("Grid9.h3.w19".equals(code)) obj = "７．経管栄養・「日に1回交換」　見出し";
        else if ("Grid9.h3.w22".equals(code)) obj = "７．経管栄養・「）」　見出し";
        
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete begin 【2012年度対応】訪問指示書の留置カテーテル部位追加
//        else if ("Grid9.h4.w3".equals(code)) obj = "８．留置カテーテル・「8」　見出し";
//        else if ("Grid9.h4.w4".equals(code)) obj = "８．留置カテーテル・「留置カテーテル（サイズ」　見出し";
//        else if ("Grid9.h4.w9".equals(code)) obj = "８．留置カテーテル　サイズ";
//        else if ("Grid9.h4.w13".equals(code)) obj = "８．留置カテーテル・「、」　見出し";
//        else if ("Grid9.h4.w18".equals(code)) obj = "８．留置カテーテル　日";
//        else if ("Grid9.h4.w19".equals(code)) obj = "８．留置カテーテル・「 日に1回交換」　見出し";
//        else if ("Grid9.h4.w22".equals(code)) obj = "８．留置カテーテル・「）」　見出し";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete end
    	//[ID:0000689][Shin Fujihara] 2012/03/12 add begin 【2012年度対応】訪問指示書の留置カテーテル部位追加
        else if ("Grid9.h4.w3".equals(code)) obj = "８．留置カテーテル・「8」　見出し";
        else if ("Grid9.h4.w4".equals(code)) obj = "８．留置カテーテル・「留置カテーテル（部位：」　見出し";
        else if ("Grid9.h4.w23".equals(code)) obj = "８．留置カテーテル　部位";
        else if ("Grid9.h4.w12".equals(code)) obj = "８．留置カテーテル・「留置カテーテル（サイズ」　見出し";
        else if ("Grid9.h4.w15".equals(code)) obj = "８．留置カテーテル　サイズ";
        else if ("Grid9.h4.w17".equals(code)) obj = "８．留置カテーテル・「、」　見出し";
        else if ("Grid9.h4.w18".equals(code)) obj = "８．留置カテーテル　日";
        else if ("Grid9.h4.w19".equals(code)) obj = "８．留置カテーテル・「 日に1回交換」　見出し";
        else if ("Grid9.h4.w22".equals(code)) obj = "８．留置カテーテル・「）」　見出し";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 add end
        
        else if ("Grid9.h5.w3".equals(code)) obj = "９．人工呼吸器・「9」　見出し";
        else if ("Grid9.h5.w4".equals(code)) obj = "９．人工呼吸器・「人工呼吸器　　（」　見出し";
        else if ("Grid9.h5.w8".equals(code)) obj = "９．人工呼吸器　種類";
        else if ("Grid9.h5.w10".equals(code)) obj = "９．人工呼吸器・「：設定」　見出し";
        else if ("Grid9.h5.w6".equals(code)) obj = "９．人工呼吸器　設定";
        else if ("Grid9.h5.w22".equals(code)) obj = "９．人工呼吸器・「）」　見出し";
        
        else if ("Grid9.h6.w3".equals(code)) obj = "１０．気管カニューレ・「10｣　見出し";
        else if ("Grid9.h6.w4".equals(code)) obj = "１０．気管カニューレ・「気管カニューレ（サイズ」　見出し";
        else if ("Grid9.h6.w9".equals(code)) obj = "１０．気管カニューレ　サイズ";
        else if ("Grid9.h6.w11".equals(code)) obj = "１０．気管カニューレ・「）」　見出し";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete begin 【2012年度対応】訪問指示書のドレーン削除
//        else if ("Grid9.h6.w12".equals(code)) obj = "１１．ドレーン・「11」　見出し";
//        else if ("Grid9.h6.w13".equals(code)) obj = "１１．ドレーン・「ドレーン（部位：」　見出し";
//        else if ("Grid9.h6.w20".equals(code)) obj = "１１．ドレーン　部位";
//        else if ("Grid9.h6.w22".equals(code)) obj = "１１．ドレーン・「）」　見出し";
//        else if ("Grid9.h7.w3".equals(code)) obj = "１２．人工肛門・「12」　見出し";
//        else if ("Grid9.h7.w4".equals(code)) obj = "１２．人工肛門・「人工肛門」　見出し";
//        else if ("Grid9.h7.w5".equals(code)) obj = "１３．人工膀胱・「13」　見出し";
//        else if ("Grid9.h7.w23".equals(code)) obj = "１３．人工膀胱・「人工膀胱」　見出し";
//        else if ("Grid9.h7.w10".equals(code)) obj = "１４・その他・「14」　見出し";
//        else if ("Grid9.h7.w11".equals(code)) obj = "１４・その他・「その他（」　見出し";
//        else if ("Grid9.h7.w14".equals(code)) obj = "１４．その他";
//        else if ("Grid9.h7.w22".equals(code)) obj = "１４・その他・「）」　見出し";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete end
    	//[ID:0000689][Shin Fujihara] 2012/03/12 add begin 【2012年度対応】訪問指示書のドレーン削除
        else if ("Grid9.h7.w3".equals(code)) obj = "１１．人工肛門・「11」　見出し";
        else if ("Grid9.h7.w4".equals(code)) obj = "１１．人工肛門・「人工肛門」　見出し";
        else if ("Grid9.h7.w5".equals(code)) obj = "１２．人工膀胱・「12」　見出し";
        else if ("Grid9.h7.w23".equals(code)) obj = "１２．人工膀胱・「人工膀胱」　見出し";
        else if ("Grid9.h7.w10".equals(code)) obj = "１３・その他・「13」　見出し";
        else if ("Grid9.h7.w11".equals(code)) obj = "１３・その他・「その他（」　見出し";
        else if ("Grid9.h7.w14".equals(code)) obj = "１３．その他";
        else if ("Grid9.h7.w22".equals(code)) obj = "１３・その他・「）」　見出し";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 add end
        else if ("Grid7.h1.w1".equals(code)) obj = "＜次頁へ続く＞";
    	
        return obj;
    }
    
    /**
     * 訪問看護指示書（医療機関）2ページ目の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatShijisho_M2(String code, Object obj){
    	
    	if ("patientData.h1.w2".equals(code)) obj = "氏名・年齢「年齢」見出し";
    	else if ("patientData.h1.w4".equals(code)) obj = "氏名・年齢「歳」見出し";
    	
        else if ("Grid10".equals(code)) obj = "留意事項及び指示事項　枠";
        else if ("Grid10.h1.w1".equals(code)) obj = "「留意事項及び指示事項」　見出し";
        else if ("Grid10.h13.w1".equals(code)) obj = "「 I 療養生活指導上の留意事項」　見出し";
        else if ("Grid10.h3.w1".equals(code)) obj = "「II」 　見出し";
    	
        else if ("Label19".equals(code)) obj = "「１. リハビリテーション」　見出し";
        else if ("Label20".equals(code)) obj = "「２. 褥瘡の処置等」　見出し";
        else if ("Label21".equals(code)) obj = "「３. 装着・使用医療機器等の操作援助・管理」　見出し";
        else if ("Label22".equals(code)) obj = "「４. その他」　見出し";
    	
        else if ("lblRyoyo".equals(code)) obj = " I 療養生活指導上の留意事項";
        else if ("lblRiha".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「１. リハビリテーション」";
        else if ("lblJyokusyo".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「２. 褥瘡の処置等」";
        else if ("lblSochaku".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「３. 装着・使用医療機器等の操作援助・管理」";
    	else if ("lblEtc".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「４．その他」";
    	
        else if ("Label24".equals(code)) obj = "「在宅患者訪問点滴注射に関する指示（投与薬剤・投与量・投与方法等）」　見出し";
        else if ("lblZaitaku".equals(code)) obj = "在宅患者訪問点滴注射に関する指示（投与薬剤・投与量・投与方法等）";
    	
        else if ("Grid11.h1.w1".equals(code)) obj = "「緊急時の連絡先」　見出し";
        else if ("Grid11.h1.w2".equals(code)) obj = "緊急時の連絡先";
        else if ("Grid11.h2.w1".equals(code)) obj = "「不在時の対応法」　見出し";
        else if ("Grid11.h2.w2".equals(code)) obj = "不在時の対応法";
    	
        else if ("Grid12.h1.w1".equals(code)) obj = "特記すべき留意事項・「特記すべき留意事項」　見出し";
        else if ("Grid12.h1.w6".equals(code)) obj = "特記すべき留意事項・「（注：薬の相互作用・副作用についての留意点、薬物アレルギーの既往等あれば記載して下さい。）」　見出し";
        else if ("lblTokki".equals(code)) obj = "特記すべき留意事項";
        else if ("Grid12.h3.w1".equals(code)) obj = "他の訪問看護ステーションへの指示・「他の訪問看護ステーションへの指示」　見出し";
        else if ("Grid12.h4.w1".equals(code)) obj = "他の訪問看護ステーションへの指示・「（」　見出し ";
        else if ("Grid12.h4.w2".equals(code)) obj = "他の訪問看護ステーションへの指示・「無」　見出し";
        else if ("Grid12.h4.w4".equals(code)) obj = "他の訪問看護ステーションへの指示・「有」　見出し";
        else if ("Grid12.h4.w5".equals(code)) obj = "他の訪問看護ステーションへの指示・「：指定訪問看護ステーション名」　見出し";
        else if ("Grid12.h4.w8".equals(code)) obj = "他の訪問看護ステーション";
        else if ("Grid12.h4.w7".equals(code)) obj = "他の訪問看護ステーションへの指示・「）」　見出し";
        
    	//[ID:0000732][Shin Fujihara] 2012/04/20 add begin 【2012年度対応：訪問看護指示書】たん吸引指示追加
        else if ("Grid12.h6.w1".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「たんの吸引等実施のための訪問介護事業所への指示」　見出し";
        else if ("Grid12.h8.w1".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「（」　見出し ";
        else if ("Grid12.h8.w2".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「無」　見出し";
        else if ("Grid12.h8.w4".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「有」　見出し";
        else if ("Grid12.h8.w5".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「：訪問介護事業所名」　見出し";
        else if ("Grid12.h8.w8".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所";
        else if ("Grid12.h8.w7".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「）」　見出し";
    	//[ID:0000732][Shin Fujihara] 2012/04/20 add end 【2012年度対応：訪問看護指示書】たん吸引指示追加
    	
        else if ("Label1".equals(code)) obj = "「上記のとおり、指定訪問看護の実施を指示いたします。」　見出し";
        else if ("Label2".equals(code)) obj = "作成年月日　「平成  年  月  日」";
    	
        else if ("Grid13.h1.w1".equals(code)) obj = "「医療機関名」　見出し";
        else if ("Grid13.h1.w2".equals(code)) obj = "医療機関名";
        else if ("Grid13.h2.w1".equals(code)) obj = "「住　　　所」　見出し";
        else if ("Grid13.h2.w2".equals(code)) obj = "医療機関住所";
        else if ("Grid13.h3.w1".equals(code)) obj = "「電　　　話」　見出し";
        else if ("Grid13.h3.w2".equals(code)) obj = "医療機関　電話番号";
        else if ("Grid13.h4.w1".equals(code)) obj = "「（ＦＡＸ）」　見出し";
        else if ("Grid13.h4.w2".equals(code)) obj = "医療機関　ＦＡＸ番号";
        else if ("Grid13.h5.w1".equals(code)) obj = "「医療機関医師氏名」　見出し";
        else if ("Grid13.h5.w2".equals(code)) obj = "医療機関医師氏名";
    	
        else if ("Label3".equals(code)) obj = "訪問看護ステーション　「印」　見出し";
        else if ("Label6".equals(code)) obj = "訪問看護ステーション名";

        return obj;
    }
    
    
    /**
     * 訪問看護指示書（介護老人保健施設）1ページ目の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatShijishoB_M1(String code, Object obj){
    	
    	if ("title".equals(code)) obj = "「訪問看護指示書」　見出し";
    	
        else if ("Grid1".equals(code)) obj = "訪問看護指示期間・点滴注射指示期間　枠";
        else if ("Grid1.h1.w14".equals(code)) obj = "「訪問看護指示期間」　見出し";
        else if ("Grid1.h1.w2".equals(code)) obj = "訪問看護指示期間　開始年";
        else if ("Grid1.h1.w3".equals(code)) obj = "訪問看護指示期間　開始年　見出し";
        else if ("Grid1.h1.w4".equals(code)) obj = "訪問看護指示期間　開始月";
        else if ("Grid1.h1.w5".equals(code)) obj = "訪問看護指示期間　開始月　見出し";
        else if ("Grid1.h1.w6".equals(code)) obj = "訪問看護指示期間　開始日";
        else if ("Grid1.h1.w7".equals(code)) obj = "訪問看護指示期間　開始日　見出し";
        else if ("Grid1.h1.w8".equals(code)) obj = "訪問看護指示期間　終了年";
        else if ("Grid1.h1.w9".equals(code)) obj = "訪問看護指示期間　終了年　見出し";
        else if ("Grid1.h1.w10".equals(code)) obj = "訪問看護指示期間　終了月";
        else if ("Grid1.h1.w11".equals(code)) obj = "訪問看護指示期間　終了月　見出し";
        else if ("Grid1.h1.w12".equals(code)) obj = "訪問看護指示期間　終了日";
        else if ("Grid1.h1.w13".equals(code)) obj = "訪問看護指示期間　終了日　見出し";
        else if ("Grid1.h2.w14".equals(code)) obj = "「点滴注射指示期間」　見出し";
        else if ("Grid1.h2.w2".equals(code)) obj = "点滴注射指示期間　開始年";
        else if ("Grid1.h2.w3".equals(code)) obj = "点滴注射指示期間　開始年　見出し";
        else if ("Grid1.h2.w4".equals(code)) obj = "点滴注射指示期間　開始月";
        else if ("Grid1.h2.w5".equals(code)) obj = "点滴注射指示期間　開始月　見出し";
        else if ("Grid1.h2.w6".equals(code)) obj = "点滴注射指示期間　開始日";
        else if ("Grid1.h2.w7".equals(code)) obj = "点滴注射指示期間　開始日　見出し";
        else if ("Grid1.h2.w8".equals(code)) obj = "点滴注射指示期間　終了年";
        else if ("Grid1.h2.w9".equals(code)) obj = "点滴注射指示期間　終了年　見出し";
        else if ("Grid1.h2.w10".equals(code)) obj = "点滴注射指示期間　終了月";
        else if ("Grid1.h2.w11".equals(code)) obj = "点滴注射指示期間　終了月　見出し";
        else if ("Grid1.h2.w12".equals(code)) obj = "点滴注射指示期間　終了日";
        else if ("Grid1.h2.w13".equals(code)) obj = "点滴注射指示期間　終了日　見出し";
    	
        else if ("Grid2.h1.w1".equals(code)) obj = "「入所者氏名」　見出し";
        else if ("Grid2.h1.w2".equals(code)) obj = "入所者氏名";
        else if ("Grid2.h1.w3".equals(code)) obj = "「生年月日」";
        else if ("Grid2.h1.w5".equals(code)) obj = "生年月日・年";
        else if ("Grid2.h1.w6".equals(code)) obj = "生年月日・年　見出し";
        else if ("Grid2.h1.w7".equals(code)) obj = "生年月日・月";
        else if ("Grid2.h1.w8".equals(code)) obj = "生年月日・月　見出し";
        else if ("Grid2.h1.w9".equals(code)) obj = "生年月日・日";
        else if ("Grid2.h1.w10".equals(code)) obj = "生年月日・日　見出し";
        else if ("Grid2.h1.w12".equals(code)) obj = "歳）";
        
        else if ("Label7".equals(code)) obj = "生年月日・元号　「明｣　見出し";
        else if ("Label8".equals(code)) obj = "生年月日・元号　「大」　見出し";
        else if ("Label9".equals(code)) obj = "生年月日・元号　「昭」　見出し";
        else if ("Label10".equals(code)) obj = "生年月日・元号　「平」　見出し";
    	
    	
        else if ("Grid3.h1.w1".equals(code)) obj = "「入所者住所」　見出し";
        else if ("Grid3.h1.w3".equals(code)) obj = "「〒」　見出し";
        else if ("Grid3.h1.w4".equals(code)) obj = "入所者郵便番号";
        else if ("Grid3.h2.w4".equals(code)) obj = "入所者住所";
        else if ("Grid3.h3.w2".equals(code)) obj = "電話番号";
        else if ("Grid3.h3.w6".equals(code)) obj = "「電話」　見出し";

        else if ("Grid4.h1.w1".equals(code)) obj = "「主たる傷病名」　見出し";
        else if ("Grid4.h1.w2".equals(code)) obj = "主たる傷病名";
    	
        else if ("Grid5.h1.w1".equals(code)) obj = "「現在の状況」　見出し";
    	
        else if ("Grid6.h1.w1".equals(code)) obj = "「症状・治療状態」　見出し";
        else if ("lblJyotai".equals(code)) obj = "症状・治療状態";

        else if ("sickMedicines8.h1.w1".equals(code)) obj = "投与中の薬剤の用法・用量・「投与中の薬剤の用法・用量」　見出し";
        else if ("sickMedicines8.h1.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「１」　見出し";
        else if ("sickMedicines8.h1.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「１」";
        else if ("sickMedicines8.h1.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「２」　見出し";
        else if ("sickMedicines8.h1.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「２」";
        else if ("sickMedicines8.h2.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「３」　見出し";
        else if ("sickMedicines8.h2.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「３」";
        else if ("sickMedicines8.h2.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「４」　見出し";
        else if ("sickMedicines8.h2.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「４」";
        else if ("sickMedicines8.h3.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「５」　見出し";
        else if ("sickMedicines8.h3.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「５」";
        else if ("sickMedicines8.h3.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「６」　見出し";
        else if ("sickMedicines8.h3.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「６」";
        else if ("sickMedicines8.h4.w5".equals(code)) obj = "投与中の薬剤の用法・用量・「７」　見出し";
        else if ("sickMedicines8.h4.w4".equals(code)) obj = "投与中の薬剤の用法・用量・「７」";
        else if ("sickMedicines8.h4.w3".equals(code)) obj = "投与中の薬剤の用法・用量・「８」　見出し";
        else if ("sickMedicines8.h4.w2".equals(code)) obj = "投与中の薬剤の用法・用量・「８」";
    	
        else if ("Grid8".equals(code)) obj = "日常生活　枠";
        else if ("Grid8.h1.w1".equals(code)) obj = "日常生活自立度　「日常生活」　見出し";
        else if ("Grid8.h1.w2".equals(code)) obj = "「寝たきり度」　見出し";
        else if ("Grid8.h1.w19".equals(code)) obj = " J1";
        else if ("Grid8.h1.w17".equals(code)) obj = " J2";
        else if ("Grid8.h1.w15".equals(code)) obj = " A1";
        else if ("Grid8.h1.w13".equals(code)) obj = " A2";
        else if ("Grid8.h1.w11".equals(code)) obj = " B1";
        else if ("Grid8.h1.w9".equals(code)) obj = " B2";
        else if ("Grid8.h1.w7".equals(code)) obj = " C1";
        else if ("Grid8.h1.w5".equals(code)) obj = " C2";
        else if ("Grid8.h2.w1".equals(code)) obj = "日常生活自立度　「自 立 度」　見出し";
        else if ("Grid8.h2.w2".equals(code)) obj = "「認知症の状況」　見出し";
        else if ("Grid8.h2.w19".equals(code)) obj = " I";
        else if ("Grid8.h2.w17".equals(code)) obj = " IIａ";
        else if ("Grid8.h2.w15".equals(code)) obj = " IIｂ";
        else if ("Grid8.h2.w13".equals(code)) obj = " IIIａ";
        else if ("Grid8.h2.w11".equals(code)) obj = " IIIｂ";
        else if ("Grid8.h2.w9".equals(code)) obj = " IV";
        else if ("Grid8.h2.w7".equals(code)) obj = " Ｍ";

        else if ("Label23".equals(code)) obj = "「要介護認定の状況」　見出し";
        else if ("Grid8.h3.w21".equals(code)) obj = "自立";
        else if ("Grid8.h3.w19".equals(code)) obj = "要支援";
        else if ("Grid8.h3.w17".equals(code)) obj = "要介護";
    	
        else if ("Label18".equals(code)) obj = "要介護認定の状況　「５ ）」";
        else if ("Label17".equals(code)) obj = "要介護認定の状況　「４」";
        else if ("Label16".equals(code)) obj = "要介護認定の状況　「３」";
        else if ("Label15".equals(code)) obj = "要介護認定の状況　「２」";
        else if ("Label14".equals(code)) obj = "要介護認定の状況　「１」";
    	
        else if ("Label26".equals(code)) obj = "「褥瘡の深さ」　見出し";
        else if ("Grid15.h3.w22".equals(code)) obj = "褥瘡の深さ　「NPUAP分類」";
        else if ("Grid15.h3.w20".equals(code)) obj = "褥瘡の深さ・NPUAP分類　「Ⅲ度」";
        else if ("Grid15.h3.w17".equals(code)) obj = "褥瘡の深さ・NPUAP分類　「Ⅳ度」";
        else if ("Grid15.h3.w15".equals(code)) obj = "褥瘡の深さ　「DESIGN分類」";
        else if ("Grid15.h3.w13".equals(code)) obj = "褥瘡の深さ・DESIGN分類　「D3」";
        else if ("Grid15.h3.w11".equals(code)) obj = "褥瘡の深さ・DESIGN分類　「D4」";
        else if ("Grid15.h3.w9".equals(code)) obj = "褥瘡の深さ・DESIGN分類　「D5」";
    	
        else if ("Grid9".equals(code)) obj = "「装着・使用医療機器等」　枠";
        else if ("Grid9.h1.w1".equals(code)) obj = "「装着・使用医療機器等」　見出し";
        else if ("Grid9.h1.w3".equals(code)) obj = "１．自動腹膜灌流装置・「1」　見出し";
        else if ("Grid9.h1.w4".equals(code)) obj = "１．自動腹膜灌流装置・「自動腹膜灌流装置」　見出し";
        else if ("Grid9.h1.w7".equals(code)) obj = "２．透析液供給装置・「2」　見出し";
        else if ("Grid9.h1.w8".equals(code)) obj = "２．透析液供給装置・「透析液供給装置」　見出し";
        else if ("Grid9.h1.w15".equals(code)) obj = "３．酸素療法・「3」　見出し";
        else if ("Grid9.h1.w24".equals(code)) obj = "３．酸素療法・「酸素療法（」　見出し";
        else if ("Grid9.h1.w20".equals(code)) obj = "３．酸素療法";
        else if ("Grid9.h1.w21".equals(code)) obj = "３．酸素療法・「 l/min」　見出し";
        else if ("Grid9.h1.w22".equals(code)) obj = "３．酸素療法・「）」　見出し";
        
        else if ("Grid9.h2.w3".equals(code)) obj = "４．吸引器・「4」　見出し";
        else if ("Grid9.h2.w4".equals(code)) obj = "４．吸引器・「吸引器」　見出し";
        else if ("Grid9.h2.w7".equals(code)) obj = "５．中心静脈栄養・「5」　見出し";
        else if ("Grid9.h2.w8".equals(code)) obj = "５．中心静脈栄養・「中心静脈栄養」　見出し";
        else if ("Grid9.h2.w15".equals(code)) obj = "６．輸液ポンプ・「6」　見出し";
        else if ("Grid9.h2.w24".equals(code)) obj = "６．輸液ポンプ・「輸液ポンプ」　見出し";
        
        else if ("Grid9.h3.w3".equals(code)) obj = "７．経管栄養・「7」　見出し";
        else if ("Grid9.h3.w4".equals(code)) obj = "７．経管栄養・「経管栄養　　　（」　見出し";
        else if ("Grid9.h3.w23".equals(code)) obj = "７．経管栄養";
        else if ("Grid9.h3.w9".equals(code)) obj = "７．経管栄養・「：チューブサイズ」　見出し";
        else if ("Grid9.h3.w15".equals(code)) obj = "７．経管栄養・「：チューブサイズ」";
        else if ("Grid9.h3.w16".equals(code)) obj = "７．経管栄養・「、」　見出し";
        else if ("Grid9.h3.w18".equals(code)) obj = "７．経管栄養　日";
        else if ("Grid9.h3.w19".equals(code)) obj = "７．経管栄養・「日に1回交換」　見出し";
        else if ("Grid9.h3.w22".equals(code)) obj = "７．経管栄養・「）」　見出し";
        
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete begin 【2012年度対応】訪問指示書の留置カテーテル部位追加
//        else if ("Grid9.h4.w3".equals(code)) obj = "８．留置カテーテル・「8」　見出し";
//        else if ("Grid9.h4.w4".equals(code)) obj = "８．留置カテーテル・「留置カテーテル（サイズ」　見出し";
//        else if ("Grid9.h4.w9".equals(code)) obj = "８．留置カテーテル　サイズ";
//        else if ("Grid9.h4.w13".equals(code)) obj = "８．留置カテーテル・「、」　見出し";
//        else if ("Grid9.h4.w18".equals(code)) obj = "８．留置カテーテル　日";
//        else if ("Grid9.h4.w19".equals(code)) obj = "８．留置カテーテル・「 日に1回交換」　見出し";
//        else if ("Grid9.h4.w22".equals(code)) obj = "８．留置カテーテル・「）」　見出し";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete end
        //[ID:0000689][Shin Fujihara] 2012/03/12 add begin 【2012年度対応】訪問指示書の留置カテーテル部位追加
        else if ("Grid9.h4.w3".equals(code)) obj = "８．留置カテーテル・「8」　見出し";
        else if ("Grid9.h4.w4".equals(code)) obj = "８．留置カテーテル・「留置カテーテル（部位：」　見出し";
        else if ("Grid9.h4.w23".equals(code)) obj = "８．留置カテーテル　部位";
        else if ("Grid9.h4.w12".equals(code)) obj = "８．留置カテーテル・「留置カテーテル（サイズ」　見出し";
        else if ("Grid9.h4.w15".equals(code)) obj = "８．留置カテーテル　サイズ";
        else if ("Grid9.h4.w17".equals(code)) obj = "８．留置カテーテル・「、」　見出し";
        else if ("Grid9.h4.w18".equals(code)) obj = "８．留置カテーテル　日";
        else if ("Grid9.h4.w19".equals(code)) obj = "８．留置カテーテル・「 日に1回交換」　見出し";
        else if ("Grid9.h4.w22".equals(code)) obj = "８．留置カテーテル・「）」　見出し";
        //[ID:0000689][Shin Fujihara] 2012/03/12 add end
        
        else if ("Grid9.h5.w3".equals(code)) obj = "９．人工呼吸器・「9」　見出し";
        else if ("Grid9.h5.w4".equals(code)) obj = "９．人工呼吸器・「人工呼吸器　　（」　見出し";
        else if ("Grid9.h5.w8".equals(code)) obj = "９．人工呼吸器　種類";
        else if ("Grid9.h5.w10".equals(code)) obj = "９．人工呼吸器・「：設定」　見出し";
        else if ("Grid9.h5.w6".equals(code)) obj = "９．人工呼吸器　設定";
        else if ("Grid9.h5.w22".equals(code)) obj = "９．人工呼吸器・「）」　見出し";
        
        else if ("Grid9.h6.w3".equals(code)) obj = "１０．気管カニューレ・「10｣　見出し";
        else if ("Grid9.h6.w4".equals(code)) obj = "１０．気管カニューレ・「気管カニューレ（サイズ」　見出し";
        else if ("Grid9.h6.w9".equals(code)) obj = "１０．気管カニューレ　サイズ";
        else if ("Grid9.h6.w11".equals(code)) obj = "１０．気管カニューレ・「）」　見出し";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete begin 【2012年度対応】訪問指示書のドレーン削除
//        else if ("Grid9.h6.w12".equals(code)) obj = "１１．ドレーン・「11」　見出し";
//        else if ("Grid9.h6.w13".equals(code)) obj = "１１．ドレーン・「ドレーン（部位：」　見出し";
//        else if ("Grid9.h6.w20".equals(code)) obj = "１１．ドレーン　部位";
//        else if ("Grid9.h6.w22".equals(code)) obj = "１１．ドレーン・「）」　見出し";
//        else if ("Grid9.h7.w3".equals(code)) obj = "１２．人工肛門・「12」　見出し";
//        else if ("Grid9.h7.w4".equals(code)) obj = "１２．人工肛門・「人工肛門」　見出し";
//        else if ("Grid9.h7.w5".equals(code)) obj = "１３．人工膀胱・「13」　見出し";
//        else if ("Grid9.h7.w23".equals(code)) obj = "１３．人工膀胱・「人工膀胱」　見出し";
//        else if ("Grid9.h7.w10".equals(code)) obj = "１４・その他・「14」　見出し";
//        else if ("Grid9.h7.w11".equals(code)) obj = "１４・その他・「その他（」　見出し";
//        else if ("Grid9.h7.w14".equals(code)) obj = "１４．その他";
//        else if ("Grid9.h7.w22".equals(code)) obj = "１４・その他・「）」　見出し";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete end
    	//[ID:0000689][Shin Fujihara] 2012/03/12 add begin 【2012年度対応】訪問指示書のドレーン削除
        else if ("Grid9.h7.w3".equals(code)) obj = "１１．人工肛門・「11」　見出し";
        else if ("Grid9.h7.w4".equals(code)) obj = "１１．人工肛門・「人工肛門」　見出し";
        else if ("Grid9.h7.w5".equals(code)) obj = "１２．人工膀胱・「12」　見出し";
        else if ("Grid9.h7.w23".equals(code)) obj = "１２．人工膀胱・「人工膀胱」　見出し";
        else if ("Grid9.h7.w10".equals(code)) obj = "１３・その他・「13」　見出し";
        else if ("Grid9.h7.w11".equals(code)) obj = "１３・その他・「その他（」　見出し";
        else if ("Grid9.h7.w14".equals(code)) obj = "１３．その他";
        else if ("Grid9.h7.w22".equals(code)) obj = "１３・その他・「）」　見出し";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 add end
        else if ("Grid7.h1.w1".equals(code)) obj = "＜次頁へ続く＞";
    	
    	
        return obj;
    }

    /**
     * 訪問看護指示書（介護老人保健施設）2ページ目の定義体IDをフォーマット化します。
     * @param code コード
     * @param obj 変換前
     * @return 変換結果
     */
    protected Object formatShijishoB_M2(String code, Object obj){
    	if ("patientData.h1.w2".equals(code)) obj = "氏名・年齢「年齢」見出し";
    	else if ("patientData.h1.w4".equals(code)) obj = "氏名・年齢「歳」見出し";
    	
        else if ("Grid10".equals(code)) obj = "留意事項及び指示事項　枠";
        else if ("Grid10.h1.w1".equals(code)) obj = "「留意事項及び指示事項」　見出し";
        else if ("Grid10.h13.w1".equals(code)) obj = "「 I 療養生活指導上の留意事項」　見出し";
        else if ("Grid10.h3.w1".equals(code)) obj = "「II」 　見出し";
    	
        else if ("Label19".equals(code)) obj = "「１. リハビリテーション」　見出し";
        else if ("Label20".equals(code)) obj = "「２. 褥瘡の処置等」　見出し";
        else if ("Label21".equals(code)) obj = "「３. 装着・使用医療機器等の操作援助・管理」　見出し";
        else if ("Label22".equals(code)) obj = "「４. その他」　見出し";
    	
        else if ("lblRyoyo".equals(code)) obj = " I 療養生活指導上の留意事項";
        else if ("lblRiha".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「１. リハビリテーション」";
        else if ("lblJyokusyo".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「２. 褥瘡の処置等」";
        else if ("lblSochaku".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「３. 装着・使用医療機器等の操作援助・管理」";
    	else if ("lblEtc".equals(code)) obj = "留意事項及び指示事項・Ⅱ　「４．その他」";
    	
        else if ("Label24".equals(code)) obj = "「在宅患者訪問点滴注射に関する指示（投与薬剤・投与量・投与方法等）」　見出し";
        else if ("lblZaitaku".equals(code)) obj = "在宅患者訪問点滴注射に関する指示（投与薬剤・投与量・投与方法等）";
    	
        else if ("Grid11.h1.w1".equals(code)) obj = "「緊急時の連絡先」　見出し";
        else if ("Grid11.h1.w2".equals(code)) obj = "緊急時の連絡先";
        else if ("Grid11.h2.w1".equals(code)) obj = "「不在時の対応法」　見出し";
        else if ("Grid11.h2.w2".equals(code)) obj = "不在時の対応法";
    	
        else if ("Grid12.h1.w1".equals(code)) obj = "特記すべき留意事項・「特記すべき留意事項」　見出し";
        else if ("Grid12.h1.w6".equals(code)) obj = "特記すべき留意事項・「（注：薬の相互作用・副作用についての留意点、薬物アレルギーの既往等あれば記載して下さい。）」　見出し";
        else if ("lblTokki".equals(code)) obj = "特記すべき留意事項";
        else if ("Grid12.h3.w1".equals(code)) obj = "他の訪問看護ステーションへの指示・「他の訪問看護ステーションへの指示」　見出し";
        else if ("Grid12.h4.w1".equals(code)) obj = "他の訪問看護ステーションへの指示・「（」　見出し ";
        else if ("Grid12.h4.w2".equals(code)) obj = "他の訪問看護ステーションへの指示・「無」　見出し";
        else if ("Grid12.h4.w4".equals(code)) obj = "他の訪問看護ステーションへの指示・「有」　見出し";
        else if ("Grid12.h4.w5".equals(code)) obj = "他の訪問看護ステーションへの指示・「：指定訪問看護ステーション名」　見出し";
        else if ("Grid12.h4.w8".equals(code)) obj = "他の訪問看護ステーション";
        else if ("Grid12.h4.w7".equals(code)) obj = "他の訪問看護ステーションへの指示・「）」　見出し";
        
        //[ID:0000732][Shin Fujihara] 2012/04/20 add begin 【2012年度対応：訪問看護指示書】たん吸引指示追加
        else if ("Grid12.h6.w1".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「たんの吸引等実施のための訪問介護事業所への指示」　見出し";
        else if ("Grid12.h8.w1".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「（」　見出し ";
        else if ("Grid12.h8.w2".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「無」　見出し";
        else if ("Grid12.h8.w4".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「有」　見出し";
        else if ("Grid12.h8.w5".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「：訪問介護事業所名」　見出し";
        else if ("Grid12.h8.w8".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所";
        else if ("Grid12.h8.w7".equals(code)) obj = "たんの吸引等実施のための訪問介護事業所への指示・「）」　見出し";
        //[ID:0000732][Shin Fujihara] 2012/04/20 add end 【2012年度対応：訪問看護指示書】たん吸引指示追加
    	
        else if ("Label1".equals(code)) obj = "「上記のとおり、指定訪問看護の実施を指示いたします。」　見出し";
        else if ("Label2".equals(code)) obj = "作成年月日　「平成  年  月  日」";
    	
        else if ("Grid13.h1.w1".equals(code)) obj = "「介護老人保健施設名」　見出し";
        else if ("Grid13.h1.w2".equals(code)) obj = "介護老人保健施設名";
        else if ("Grid13.h2.w1".equals(code)) obj = "「住　　　所」　見出し";
        else if ("Grid13.h2.w2".equals(code)) obj = "介護老人保健施設名住所";
        else if ("Grid13.h3.w1".equals(code)) obj = "「電　　　話」　見出し";
        else if ("Grid13.h3.w2".equals(code)) obj = "介護老人保健施設名　電話番号";
        else if ("Grid13.h4.w1".equals(code)) obj = "「（ＦＡＸ）」　見出し";
        else if ("Grid13.h4.w2".equals(code)) obj = "介護老人保健施設名　ＦＡＸ番号";
        else if ("Grid13.h5.w1".equals(code)) obj = "「介護老人保健施設医師氏名」　見出し";
        else if ("Grid13.h5.w2".equals(code)) obj = "介護老人保健施設医師氏名";
    	
        else if ("Label3".equals(code)) obj = "訪問看護ステーション　「印」　見出し";
        else if ("Label6".equals(code)) obj = "訪問看護ステーション名";
    	
        return obj;
    }

    //[ID:0000639][Shin Fujihara] 2011/03 add end

}
