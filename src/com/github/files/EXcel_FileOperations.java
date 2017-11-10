package com.github.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.ExtendedColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.stage.StageStyle;

/**
 * https://poi.apache.org/spreadsheet/quick-guide.html
 * 
 * https://www.computerhope.com/shortcut/excel.htm
 * https://support.office.com/en-us/article/Excel-keyboard-shortcuts-and-function-keys-for-Windows-1798d9d5-842a-42b8-9c99-9b7213f0040f
 * https://www.guru99.com/all-about-excel-in-selenium-poi-jxl.html
 * 
 * @author yashwanth.m
 *
 */
public class EXcel_FileOperations {
	static org.apache.poi.ss.usermodel.Workbook workbook = null;
	
	// Selection.EntireRow.Select With 
	// Selection.Interior.Color = 49407
	// Range(Cells(Rng.Row, "A"), Cells(Rng.Row, "P")).Interior.Color = 49407
	public static void main(String[] args) {
		/*ClassLoader classloader = org.apache.poi.poifs.filesystem.POIFSFileSystem.class.getClassLoader();
		URL res = classloader.getResource( "org/apache/poi/poifs/filesystem/POIFSFileSystem.class" );
		String path = res.getPath();
		System.out.println("Core POI came from " + path);*/
				
		String fileName = "parametarisationTset.xlsx", sheetName = "loginTest";
		Rewriting(fileName, sheetName, true);
		//Reading(fileName, sheetName);
		
	}
	
	
	public static void get_WorkbookObject( String fileExtensionName, FileInputStream stream) {
		//Find the file extension by splitting file name in substring  and getting only extension name
		try {
			if( fileExtensionName.equals(".xlsx") ) {
				workbook = new XSSFWorkbook( stream );
			} else if( fileExtensionName.equals(".xls") ) {
				workbook = new HSSFWorkbook( stream );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static short getCustomColorCode(int red, int green, int blue, int alpha) {
		/*org.apache.poi.ss.usermodel.ColorScaleFormatting
		org.apache.poi.ss.usermodel.Color
		org.apache.poi.ss.usermodel.ExtendedColor
		org.apache.poi.ss.usermodel.IndexedColors*/
		int[] rgb = new int[]{ red, green, blue, alpha };
		java.awt.Color awtColor = new java.awt.Color(rgb[0], rgb[1], rgb[2]);
		XSSFColor myColor = new XSSFColor( awtColor );
		short index = myColor.getIndexed();
		System.out.println( "Index : "+ index);
		//short argb = (short) awtColor.getRGB();
		
		// create custom colors
		/*HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
		// This replaces various shades of grey with custom colors RGB red (0-255)
		palette.setColorAtIndex(// RGB red (0-255)
		HSSFColor.BLACK.index, (byte) red, (byte) blue, (byte) green);*/
		
		return index;
	}
	/**
	 * Merge and center the cells specified by range
	 * @param startCell the first cell in the cells to be merged
	 * @param range the range of the cells to be merged
	 */
	public static void mergeAndCenter(Cell startCell, CellRangeAddress range, short bg ) {
		try {
			startCell.getSheet().addMergedRegion( range );
		} catch (IllegalStateException e) {
			// Cannot add merged region C5:C7 to sheet because it overlaps with an existing merged region (C5:C7)
		}
		
		CellStyle style = startCell.getSheet().getWorkbook().createCellStyle();
		style.setAlignment( HorizontalAlignment.CENTER );
		style.setVerticalAlignment( VerticalAlignment.CENTER );
		style.setFillBackgroundColor(bg);
		System.out.println( "ARGB : "+ bg);
		System.out.println(  );
		style.setFillForegroundColor( bg );
		//style.setFillPattern( FillPatternType.SOLID_FOREGROUND );
		startCell.setCellStyle(style);
		
	}
	/**
	 * @param fileName
	 * @param sheetName
	 * @param isRewriting
	 */
	/**
	 * @param fileName
	 * @param sheetName
	 * @param isRewriting
	 */
	public static void Rewriting( String fileName, String sheetName, boolean isRewriting ) {
		try {
			
			File file = new File( fileName );
			FileInputStream inputStream = new FileInputStream(file);
			
			String fileExtensionName = fileName.substring(fileName.indexOf(".")); // fileName.split(".")[1];
			System.out.println("File Extension : "+ fileExtensionName);
			get_WorkbookObject(fileExtensionName, inputStream);
			
			Sheet sheet = workbook.getSheet(sheetName);
			
			// Avoid first Row as it contains column's descriptive names.
			int dataRowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			
			for (int i = 1; i <= dataRowCount; i++) {
				Row row = sheet.getRow(i);
				
				StringBuffer rowData = new StringBuffer();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					String stringCellValue = null;
					
					//  Checking Data Formats.
					if( cell.getCellTypeEnum() == CellType.STRING ) {
						stringCellValue = cell.getStringCellValue() +"[S]";
					} else if( cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA ) {
						String cellValue = String.valueOf(cell.getNumericCellValue());
						if( DateUtil.isCellDateFormatted(cell) ) {
							DateFormat df = new SimpleDateFormat("dd/MM/yy");
							Date date = cell.getDateCellValue();
							cellValue = df.format(date) +"[D]";
						}
						stringCellValue = cellValue;
					} else if( cell.getCellTypeEnum() == CellType.BLANK ) {
						stringCellValue = "[ ]";
					} else {
						stringCellValue = String.valueOf( cell.getBooleanCellValue() )+"[B]";
					}
					rowData.append( stringCellValue );
					
					if ( j+1 < row.getLastCellNum() ) {
						rowData.append( " ~ " );
					} else if ( j+1 == row.getLastCellNum() ) {
						
						if( isRewriting ) {
							int temp = (Math.random() <= 0.5) ? 1 : 2;
							if( temp ==  1 ) {
								
								Cell passCell = row.createCell( 5 );
								//passCell.setAsActiveCell();
								
								CellStyle pass = workbook.createCellStyle();
								pass.setVerticalAlignment( VerticalAlignment.BOTTOM );
								pass.setAlignment( HorizontalAlignment.CENTER );
									// It is applicable in Edit mode when you double click on cell.
									pass.setFillBackgroundColor( IndexedColors.YELLOW.getIndex() );
								passCell.setCellStyle(pass);
								passCell.setCellValue("Pass");
								
								Cell errorMSG = row.createCell( 6 );
								errorMSG.setCellValue(" - ");
							} else {
								
								Cell failCell = row.createCell( 5 );
								CellStyle fail = workbook.createCellStyle();
								fail.setVerticalAlignment( VerticalAlignment.BOTTOM );
								fail.setAlignment( HorizontalAlignment.CENTER );
									fail.setFillBackgroundColor( IndexedColors.RED1.getIndex() );
								
								failCell.setCellStyle( fail );
								failCell.setCellValue("Fail");
								
								Cell errorMSG = row.createCell( 6 );
								
								//to enable newlines you need set a cell styles with wrap=true
								CellStyle cs = workbook.createCellStyle();
								cs.setWrapText( true );
								errorMSG.setCellStyle(cs);
								
								StringBuffer failMessage = new StringBuffer( " Failed Error Message ");
								for (int k = 0; k < 2; k++) {
									failMessage.append(" Failed Error Message ");
								}
								errorMSG.setCellValue( failMessage.toString() );
								
								//increase row height to accomodate two lines of text
								row.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints()));
								//adjust column width to fit the content
								sheet.autoSizeColumn((short)2);
							}
							
						}
					}
					
				}
				System.out.println( rowData );
			}
			
			inputStream.close();
			
			if( isRewriting ) {
				
				// Create|Get a row and put some cells in it. Rows are 0 based.
				Row headerRow = sheet.getRow(0);
				
				// Fonts are set into a style so create a new one to use. Set style to first Row - Which contains Header Data.
				Font font = workbook.createFont();
				font.setFontName("Arial Rounded MT Bold"); // Name
				font.setItalic( false ); // Style
				font.setBold( false );
				font.setFontHeightInPoints( (short)12 ); // Size
				font.setColor( IndexedColors.OLIVE_GREEN.getIndex() ); // Color
				
				CellStyle style = workbook.createCellStyle();
				style.setFont(font);
				style.setRotation( (short)0 );
				style.setVerticalAlignment( VerticalAlignment.BOTTOM );
				style.setAlignment( HorizontalAlignment.CENTER );
				
				style.setBottomBorderColor( IndexedColors.RED.getIndex() );
				style.setBorderBottom( BorderStyle.MEDIUM );
				
				/*style.setFillBackgroundColor( IndexedColors.GREY_25_PERCENT.getIndex() );
				style.setFillPattern(FillPatternType.THIN_BACKWARD_DIAG);*/
				
				// https://stackoverflow.com/a/42004462/5081877
				style.setFillForegroundColor( IndexedColors.GREY_40_PERCENT.getIndex() );
				style.setFillPattern( FillPatternType.SOLID_FOREGROUND );
				
				//style.setLocked(true);
				for (int j = 0; j < headerRow.getLastCellNum(); j++) {
					Cell cell = headerRow.getCell(j);
					cell.setCellStyle(style);
				}
			
				int red = 215, green = 228, blue = 188, alpha = 255;
				short customColorCode = getCustomColorCode(red, green, blue, alpha);
				
				CellRangeAddress cellRangeAddress = new CellRangeAddress(4, 6, 2, 2 );
				Cell startCell = sheet.getRow( 4 ).getCell( 2 );
				mergeAndCenter(startCell , cellRangeAddress, customColorCode);
				
				CellRangeAddress filterRange = new CellRangeAddress( 0, 6, 5, 5);
				sheet.setAutoFilter( filterRange );
				
				// Write the output to a file
				FileOutputStream outputStream = new FileOutputStream( file );
				workbook.write( outputStream );
				outputStream.close();
			}
			// Exception in thread "main" org.apache.poi.EmptyFileException: The supplied file was empty (zero bytes long)
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void Reading( String fileName, String sheetName ) {
		try {
			File file = new File( fileName );
			FileInputStream inputStream = new FileInputStream(file);
			
			String fileExtensionName = fileName.substring(fileName.indexOf(".")); // fileName.split(".")[1];
			System.out.println("File Extension : "+ fileExtensionName);
			get_WorkbookObject(fileExtensionName, inputStream);
			
			Sheet sheet = workbook.getSheet(sheetName);
			
			// Avoid first Row as it contains column's descriptive names.
			int dataRowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			for (int i = 1; i <= dataRowCount; i++) {
				Row row = sheet.getRow(i);
				
				StringBuffer rowData = new StringBuffer();
				for ( int j = 0; j < row.getLastCellNum(); j++ ) {
					Cell cell = row.getCell(j);
					String stringCellValue = null;
					if( cell.getCellTypeEnum() == CellType.STRING ) {
						stringCellValue = cell.getStringCellValue() +"[S]";
					} else if( cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA ) {
						String cellValue = String.valueOf(cell.getNumericCellValue());
						if( DateUtil.isCellDateFormatted(cell) ) {
							DateFormat df = new SimpleDateFormat("dd/MM/yy");
							Date date = cell.getDateCellValue();
							cellValue = df.format(date) +"[D]";
						}
						stringCellValue = cellValue;
					} else if( cell.getCellTypeEnum() == CellType.BLANK ) {
						stringCellValue = "[ ]";
					} else {
						stringCellValue = String.valueOf( cell.getBooleanCellValue() )+"[B]";
					}
					
					rowData.append( stringCellValue );
					
					if ( j+1 < row.getLastCellNum() ) {
						rowData.append( " ~ " );
					}
					
				}
				System.out.println( rowData );
			}
			inputStream.close();
			
			// Exception in thread "main" org.apache.poi.EmptyFileException: The supplied file was empty (zero bytes long)
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
