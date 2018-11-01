package com.github.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <B>POI-HSSF and POI-XSSF - Java API To Access Microsoft Excel Format Files.</B>
 * <a href="POI-HSSF and POI-XSSF - Java API To Access Microsoft Excel Format Files">Overview</a>
 * 
 * <P>HSSF is the POI Project's pure Java implementation of the Excel '97(-2007) file format.
 * XSSF is the POI Project's pure Java implementation of the Excel 2007 OOXML (.xlsx) file format.
 * 
 * <UL>HSSF and XSSF provides ways to read spreadsheets create, modify, read and write XLS spreadsheets. They provide:
 * <LI>low level structures for those with special needs</LI>
 * <LI>an eventmodel api for efficient read-only access</LI>
 * <LI>a full usermodel api for creating, reading and modifying XLS files</LI></UL>
 * </P>
 * 
 * <p><B><a herf="http://poi.apache.org/overview.html">HSSF and XSSF for Excel Documents</a></B>
 * HSSF is our port of the Microsoft Excel 97 (-2003) file format (BIFF8) to pure Java. XSSF is our port of the Microsoft Excel XML (2007+)
 * file format (OOXML) to pure Java. SS is a package that provides common support for both formats with a common API.
 * </P>
 * 
 * https://poi.apache.org/spreadsheet/quick-guide.html
 * 
 * @author yashwanth.m
 *
 */
public class EXcel_FileOperations {
	static org.apache.poi.ss.usermodel.Workbook workbook = null;
	static boolean isXSSF = true;
	// Selection.EntireRow.Select With 
	// Selection.Interior.Color = 49407
	// Range(Cells(Rng.Row, "A"), Cells(Rng.Row, "P")).Interior.Color = 49407
	public static void main(String[] args) {
		/*ClassLoader classloader = org.apache.poi.poifs.filesystem.POIFSFileSystem.class.getClassLoader();
		URL res = classloader.getResource( "org/apache/poi/poifs/filesystem/POIFSFileSystem.class" );
		String path = res.getPath();
		System.out.println("Core POI came from " + path);*/
				
		String fileName = "parametarisation_XSSF.xlsx", sheetName = "loginTest";
		Rewriting(fileName, sheetName, true);
		//Reading(fileName, sheetName);
		
	}
	
	
	/**
	 * By auto-detecting it Creates the appropriate HSSFWorkbook / XSSFWorkbook from the given InputStream.
	 * 
	 * Workbook workbook = WorkbookFactory.create( stream );
	 * 
	 * @param fileExtensionName
	 * @param stream
	 */
	public static void get_WorkbookObject( String fileExtensionName, FileInputStream stream ) {
		//Find the file extension by splitting file name in substring  and getting only extension name
		try {
			if( fileExtensionName.equals(".xlsx") || fileExtensionName.equals(".xls") ) {
				workbook = WorkbookFactory.create( stream );
				if ( workbook instanceof XSSFWorkbook ) {
					System.out.println("XSSFWorkbook « OOXML / ZIP stream");
					isXSSF = true;
				} else {
					System.out.println("HSSFWorkbook « OLE2 / BIFF8+ stream used for Office 97 and higher documents ");
					isXSSF = false;
				}
				
				/*if( fileExtensionName.equals(".xlsx") ) {
					OPCPackage pkg = OPCPackage.open( stream );
					workbook = new XSSFWorkbook( pkg );
				} else if( fileExtensionName.equals(".xls") ) { // https://stackoverflow.com/a/9632869/5081877
					// org.apache.poi.poifs.filesystem.OfficeXmlFileException: The supplied data appears to be in the Office 2007+ XML.
					// You are calling the part of POI that deals with OLE2 Office Documents. You need to call a different part of POI
					// to process this data (eg XSSF instead of HSSF)
	
					NPOIFSFileSystem fs = new NPOIFSFileSystem( stream );
					DirectoryNode root = fs.getRoot();
					workbook = new HSSFWorkbook(root, true);
					isXSSF = false;
				}*/
			} else {
				throw new InvalidFormatException("Your Extension was neither (*.xlsx), nor (*.xlsx)");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
	}
	
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
			get_WorkbookObject(fileExtensionName, inputStream );
			
			Sheet sheet = workbook.getSheet(sheetName);
			
			int totalRows = sheet.getPhysicalNumberOfRows();
			System.out.println("Total Number of Rows : "+ totalRows );
			// Avoid first Row as it contains column's descriptive names.
			int dataRowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			
			for (int i = 1; i <= dataRowCount; i++) {
				Row row = sheet.getRow(i);
				
				StringBuffer rowData = new StringBuffer();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					String stringCellValue = getCellValue(cell);
					
					rowData.append( stringCellValue );
					
					if ( j+1 < row.getLastCellNum() ) {
						rowData.append( " ~ " );
					} else if ( j+1 == row.getLastCellNum() ) {
						
						if( isRewriting ) {
							int temp = (Math.random() <= 0.5) ? 1 : 2;
							if( temp ==  1 ) {
								
								Cell passCell = row.createCell( 5 );
								//passCell.setAsActiveCell();
								passCell.setCellStyle( getPassFailCSS(true) );
								passCell.setCellValue("Pass");
								
								Cell errorMSG = row.createCell( 6 );
								errorMSG.setCellValue(" - ");
							} else {
								
								Cell failCell = row.createCell( 5 );
								
								failCell.setCellStyle( getPassFailCSS(false) );
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
								
								//increase row height to accommodate two lines of text
								row.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
							}
							//adjust column width to fit the content
							sheet.autoSizeColumn((short)4);
						}
					}
					
				}
				System.out.println( rowData );
			}
			sheet.setDefaultRowHeight( (short) 250 );

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
			
				int red = 128, green = 0, blue = 128, alpha = 255;
				short customColorCode = getCustomColorCode(red, green, blue, alpha);
				
				CellRangeAddress cellRangeAddress = new CellRangeAddress(4, 6, 2, 2 );
				Cell startCell = sheet.getRow( 4 ).getCell( 2 );
				mergeAndCenter(startCell , cellRangeAddress, customColorCode);
				
				CellRangeAddress filterRange = new CellRangeAddress( 0, 6, 5, 5);
				sheet.setAutoFilter( filterRange );
				
				CellRangeAddressList addressList = new CellRangeAddressList(1, 6, 1, 1);
				String[] optionsList = new String[]{"SELECT", "TRUE", "FALSE"};
				setDropDownOptions( sheetName, addressList, optionsList);
				
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
			
			System.out.println(" ===== Headers =====");
			Iterator<Row> rows = sheet.rowIterator();
			if( rows.hasNext() ) {
				Row headers = (XSSFRow) rows.next();
				StringBuffer rowData = new StringBuffer();
				for ( int j = 0; j < headers.getPhysicalNumberOfCells(); j++ ) {
					Cell cell = headers.getCell(j);
					String stringCellValue = getCellValue(cell);
					
					rowData.append( stringCellValue );
					
					if ( j+1 < headers.getLastCellNum() ) {
						rowData.append( " ~ " );
					}
					
				}
				System.out.println( rowData );
			}
			System.out.println(" ===== ----- Data ----- =====");
			// Avoid first Row as it contains column's descriptive names.
			int dataRowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			for (int i = 1; i <= dataRowCount; i++) {
				Row row = sheet.getRow(i);
				
				StringBuffer rowData = new StringBuffer();
				for ( int j = 0; j < row.getLastCellNum(); j++ ) {
					Cell cell = row.getCell(j);
					String stringCellValue = getCellValue(cell);
					
					rowData.append( stringCellValue );
					
					if ( j+1 < row.getLastCellNum() ) {
						rowData.append( " ~ " );
					}
					
				}
				System.out.println( rowData );
			}
			
			inputStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * https://stackoverflow.com/a/11115379/5081877
	 * http://poi.apache.org/spreadsheet/quick-guide.html#Validation
	 * 
	 * @param fileExtensionName	the file extension to get its corresponding Helper class
	 * @param sheet			the work book sheet to avail changes
	 * @param addressList	the range of row and column numbers
	 * @param optionsList	the list of options that you want to provide
	 */
	public static void setDropDownOptions( String sheetName, CellRangeAddressList addressList, String[] optionsList) {
		DataValidationHelper validationHelper = null;
		if( isXSSF ) {
			XSSFSheet xssf = (XSSFSheet) workbook.getSheet(sheetName);
			validationHelper = new XSSFDataValidationHelper((XSSFSheet) xssf);
			DataValidationConstraint dvConstraint = validationHelper.createExplicitListConstraint( optionsList );
			DataValidation dataValidation = validationHelper.createValidation( dvConstraint, addressList);
			dataValidation.setSuppressDropDownArrow(true);
			dataValidation.setShowPromptBox(true);
			dataValidation.setEmptyCellAllowed(false);
			xssf.addValidationData(dataValidation);
		} else {
			// validationHelper = new HSSFDataValidationHelper((HSSFSheet) sheet);
			DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint( optionsList );
			DataValidation dataValidation = new HSSFDataValidation(addressList,dvConstraint);
			dataValidation.setSuppressDropDownArrow(false);
			dataValidation.setShowPromptBox(true);
			dataValidation.setEmptyCellAllowed(false);
			HSSFSheet hssf = (HSSFSheet) workbook.getSheet(sheetName);
			hssf.addValidationData(dataValidation );
		}
	}
	
	/**
	 * http://poi.apache.org/spreadsheet/quick-guide.html#CustomColors
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 * @return
	 */
	public static short getCustomColorCode(int red, int green, int blue, int alpha) {
		/*org.apache.poi.ss.usermodel.ColorScaleFormatting
		org.apache.poi.ss.usermodel.Color
		org.apache.poi.ss.usermodel.ExtendedColor
		org.apache.poi.ss.usermodel.IndexedColors*/
		int[] rgb = new int[]{ red, green, blue, alpha };
		short index = 0;
		java.awt.Color awtColor = new java.awt.Color(rgb[0], rgb[1], rgb[2]);
		if( isXSSF ) {
			XSSFColor myColor = new XSSFColor( awtColor );
			index = myColor.getIndexed();
		} else {
			HSSFColor myColor = new HSSFColor( alpha, alpha, awtColor );
			index = myColor.getIndex();
		}
		System.out.println( "Index : "+ index);
		short argb = (short) awtColor.getRGB();
		System.out.println("AWT « ARGB : "+argb);
		
		return index;
	}
	/**
	 * Merge and center the cells specified by range
	 * @param startCell the first cell in the cells to be merged
	 * @param range the range of the cells to be merged
	 */
	public static void mergeAndCenter( Cell startCell, CellRangeAddress range, short bg ) {
		try {
			startCell.getSheet().addMergedRegion( range );
		} catch (IllegalStateException e) {
			// Cannot add merged region C5:C7 to sheet because it overlaps with an existing merged region (C5:C7)
		}
		
		CellStyle style = startCell.getSheet().getWorkbook().createCellStyle();
		style.setAlignment( HorizontalAlignment.CENTER );
		style.setVerticalAlignment( VerticalAlignment.CENTER );
		style.setFillBackgroundColor(bg);
		style.setFillForegroundColor( bg );
		//style.setFillPattern( FillPatternType.SOLID_FOREGROUND );
		startCell.setCellStyle(style);
		
	}
	public static String getCellValue( Cell cell ) {
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
			} else {
				cellValue += "[N]";
			}
			stringCellValue = cellValue;
		} else if( cell.getCellTypeEnum() == CellType.BLANK ) {
			stringCellValue = "[ ]";
		} else {
			stringCellValue = String.valueOf( cell.getBooleanCellValue() )+"[B]";
		}
		return stringCellValue;
	}
	public static CellStyle getPassFailCSS( boolean isPass ) {
		CellStyle style = workbook.createCellStyle();
		if (isPass) {
			CellStyle pass = workbook.createCellStyle();
			pass.setVerticalAlignment( VerticalAlignment.BOTTOM );
			pass.setAlignment( HorizontalAlignment.CENTER );
			// It is applicable in Edit mode when you double click on cell.
			pass.setFillBackgroundColor( IndexedColors.YELLOW.getIndex() );
			
			style = pass;
		} else {
			CellStyle fail = workbook.createCellStyle();
			fail.setVerticalAlignment( VerticalAlignment.BOTTOM );
			fail.setAlignment( HorizontalAlignment.CENTER );
				fail.setFillBackgroundColor( IndexedColors.RED1.getIndex() );
			
			style = fail;
		}
		return style;
	}
}