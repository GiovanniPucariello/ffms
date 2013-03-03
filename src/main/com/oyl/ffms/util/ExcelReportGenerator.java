package com.oyl.ffms.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.oyl.base.util.DateUtil;
import com.oyl.base.util.NumberUtil;

public class ExcelReportGenerator
{
    public static byte[] getItemExcelSheel(List<Map> items, Map param)
            throws IOException, RowsExceededException, WriteException
    {
        ByteArrayOutputStream os = null;
        WritableWorkbook wwb = null;

        try
        {
            os = new ByteArrayOutputStream();

            try
            {
                wwb = Workbook.createWorkbook(os);

                WritableSheet wws = wwb.createSheet("ITEM-REPORT", 0);
                
                // set column width.
                wws.setColumnView(0, 20);
                wws.setColumnView(1, 20);
                wws.setColumnView(2, 60);
                wws.setColumnView(3, 20);
                wws.setColumnView(4, 20);
                wws.setColumnView(5, 20);
                wws.setColumnView(6, 20);
                wws.setColumnView(7, 20);

                // merge cells.
                wws.mergeCells(0, 0, 7, 0);
                wws.mergeCells(0, 1, 2, 1);
                wws.mergeCells(0, 2, 2, 2);
                wws.mergeCells(6, items.size() + 4, 7, items.size() + 4);
                wws.mergeCells(6, items.size() + 5, 7, items.size() + 5);

                // define formats.
                WritableCellFormat titleFmt = new WritableCellFormat(
                        new WritableFont(WritableFont.createFont("宋体"), 50,
                                WritableFont.BOLD));
                titleFmt.setVerticalAlignment(VerticalAlignment.CENTRE);
                titleFmt.setAlignment(Alignment.CENTRE);

                
                WritableCellFormat headerFmtLeft = new WritableCellFormat(
                        new WritableFont(WritableFont.createFont("宋体"), 25,
                                WritableFont.BOLD));
                headerFmtLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
                headerFmtLeft.setAlignment(Alignment.LEFT);

                WritableCellFormat headerFmt = new WritableCellFormat(
                        new WritableFont(WritableFont.createFont("宋体"), 25,
                                WritableFont.BOLD));
                headerFmt.setVerticalAlignment(VerticalAlignment.CENTRE);
                headerFmt.setAlignment(Alignment.CENTRE);

                
                WritableCellFormat headerFmtRight = new WritableCellFormat(
                        new WritableFont(WritableFont.createFont("宋体"), 25,
                                WritableFont.BOLD));
                headerFmtRight.setVerticalAlignment(VerticalAlignment.CENTRE);
                headerFmtRight.setAlignment(Alignment.RIGHT);

                
                WritableCellFormat defaultFmtLeft = new WritableCellFormat(
                        new WritableFont(WritableFont.createFont("宋体"), 15));
                defaultFmtLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
                defaultFmtLeft.setAlignment(Alignment.LEFT);

                
                WritableCellFormat defaultFmt = new WritableCellFormat(
                        new WritableFont(WritableFont.createFont("宋体"), 15));
                defaultFmt.setVerticalAlignment(VerticalAlignment.CENTRE);
                defaultFmt.setAlignment(Alignment.CENTRE);

                
                WritableCellFormat defaultFmtRight = new WritableCellFormat(
                        new WritableFont(WritableFont.createFont("宋体"), 15));
                defaultFmtRight.setVerticalAlignment(VerticalAlignment.CENTRE);
                defaultFmtRight.setAlignment(Alignment.RIGHT);

                
                //title
                Label family = new Label(0, 0, (String) param.get("familyName"));
                family.setCellFormat(titleFmt);

                Label startDate = new Label(0, 1, "\u8d77\u59cb\u65e5\u671f:"
                        + DateUtil.convertDateToString(
                                ((Date) param.get("fromDate")), "yyyy-MM-dd"));
                startDate.setCellFormat(headerFmtLeft);

                Label endDate = new Label(0, 2, "\u7ed3\u675f\u65e5\u671f:"
                        + DateUtil.convertDateToString(
                                ((Date) param.get("toDate")), "yyyy-MM-dd"));
                endDate.setCellFormat(headerFmtLeft);

                wws.addCell(family);
                wws.addCell(startDate);
                wws.addCell(endDate);

                
                
                //header
                Label createDateTitle = new Label(0, 3, "\u65e5\u671f");
                createDateTitle.setCellFormat(headerFmt);

                Label creatorTitle = new Label(1, 3, "\u767b\u8bb0\u4eba");
                creatorTitle.setCellFormat(headerFmt);

                Label descTitle = new Label(2, 3, "\u8d26\u5355\u63cf\u8ff0");
                descTitle.setCellFormat(headerFmt);

                Label categoryTitle = new Label(3, 3, "\u79cd\u7c7b");
                categoryTitle.setCellFormat(headerFmt);

                Label itemCostTitle = new Label(4, 3, "\u5355\u4ef7");
                itemCostTitle.setCellFormat(headerFmt);

                Label itemQuantityTitle = new Label(5, 3, "\u6570\u91cf");
                itemQuantityTitle.setCellFormat(headerFmt);

                Label outAmtTitle = new Label(6, 3, "\u652f\u51fa");
                outAmtTitle.setCellFormat(headerFmt);

                Label inAmtTitle = new Label(7, 3, "\u6536\u5165");
                inAmtTitle.setCellFormat(headerFmt);

                wws.addCell(createDateTitle);
                wws.addCell(creatorTitle);
                wws.addCell(descTitle);
                wws.addCell(categoryTitle);
                wws.addCell(itemCostTitle);
                wws.addCell(itemQuantityTitle);
                wws.addCell(outAmtTitle);
                wws.addCell(inAmtTitle);

                
                //detail
                BigDecimal totalIn = new BigDecimal(0);
                BigDecimal totalOut = new BigDecimal(0);

                for (int i = 0; i < items.size(); i++)
                {
                    Map item = (Map) items.get(i);

                    Label createDate = new Label(0, (i + 4),
                            DateUtil.convertDateToString(
                                    ((Date) item.get("date")), "yyyy-MM-dd"));
                    createDate.setCellFormat(defaultFmt);

                    Label creator = new Label(1, (i + 4),
                            (String) item.get("userAlias"));
                    creator.setCellFormat(defaultFmt);

                    Label desc = new Label(2, (i + 4),
                            (String) item.get("itemDesc"));
                    desc.setCellFormat(defaultFmtLeft);

                    Label category = new Label(3, (i + 4),
                            (String) item.get("categoryName"));
                    category.setCellFormat(defaultFmtLeft);

                    Label itemCost = new Label(4, (i + 4),
                            NumberUtil.formatAmount((BigDecimal) item
                                    .get("itemCost")));
                    itemCost.setCellFormat(defaultFmtRight);

                    Label itemQuantity = new Label(5, (i + 4),
                            NumberUtil.formatQuantity((BigDecimal) item
                                    .get("itemQuantity")));
                    itemQuantity.setCellFormat(defaultFmtRight);

                    Label outAmt = new Label(6, (i + 4),
                            NumberUtil.formatAmount((BigDecimal) item
                                    .get("outAmt")));
                    outAmt.setCellFormat(defaultFmtRight);

                    Label inAmt = new Label(7, (i + 4),
                            NumberUtil.formatAmount((BigDecimal) item
                                    .get("inAmt")));
                    inAmt.setCellFormat(defaultFmtRight);

                    wws.addCell(createDate);
                    wws.addCell(creator);
                    wws.addCell(desc);
                    wws.addCell(category);
                    wws.addCell(itemCost);
                    wws.addCell(itemQuantity);
                    wws.addCell(outAmt);
                    wws.addCell(inAmt);

                    if (null != item.get("inAmt"))
                    {
                        totalIn = totalIn.add((BigDecimal) item.get("inAmt"));
                    }

                    if (null != item.get("outAmt"))
                    {
                        totalOut = totalOut
                                .add((BigDecimal) item.get("outAmt"));
                    }

                }

                
                //summary
                Label totalInLabelDesc = new Label(5, items.size() + 4,
                        "\u603b\u6536\u5165:");
                totalInLabelDesc.setCellFormat(headerFmtLeft);

                Label totalInLabel = new Label(6, items.size() + 4,
                        NumberUtil.formatAmount(totalIn));
                totalInLabel.setCellFormat(headerFmtRight);

                Label totalOutLabelDesc = new Label(5, items.size() + 5,
                        "\u603b\u652f\u51fa:");
                totalOutLabelDesc.setCellFormat(headerFmtLeft);

                Label totalOutLabel = new Label(6, items.size() + 5,
                        NumberUtil.formatAmount(totalOut));
                totalOutLabel.setCellFormat(headerFmtRight);

                wws.addCell(totalInLabelDesc);
                wws.addCell(totalInLabel);
                wws.addCell(totalOutLabelDesc);
                wws.addCell(totalOutLabel);
            }
            finally
            {
                wwb.write();
                wwb.close();
            }

            return os.toByteArray();

        }
        finally
        {
            if (os != null)
            {
                os.close();
                os = null;
            }
        }

    }
}
