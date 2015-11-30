package org.nor.swt.tutorials;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class Letskorai {

  Map<String, Object> controlMap = new HashMap<String, Object>();
  int step = 0;
  
  public Letskorai() {
    Display display = new Display();
    Shell shell = new Shell(display);
   
    // create a new GridLayout with two columns
    // of different size
    GridLayout layout = new GridLayout(2, false);

    // set the layout to the shell
    shell.setLayout(layout);

    setParamerter(new Composite(shell, SWT.BORDER));
    setWebBrowser(new Composite(shell, SWT.BORDER));
    
    shell.pack();
    shell.open();
    
    while (!shell.isDisposed())
      if (!display.readAndDispatch())
        display.sleep();

    display.dispose();
  }
  
  public void setParamerter(Composite composite) {
    GridLayout layout = new GridLayout(2, false);
    composite.setLayout(layout);
    
    // 1. I/D
    Label label1 = new Label(composite, SWT.NONE);
    label1.setText("I/D");
    Text text1 = new Text(composite, SWT.SINGLE);
    text1.setText("");
    controlMap.put("param1", text1);
    
    // 2. P/W
    Label label2 = new Label(composite, SWT.NONE);
    label2.setText("P/W");
    Text text2 = new Text(composite, SWT.SINGLE);
    text2.setText("");
    controlMap.put("param2", text2);
    
    // 3. 출발역
    Label label3 = new Label(composite, SWT.NONE);
    label3.setText("출발역");
    Text text3 = new Text(composite, SWT.SINGLE);
    text3.setText("정동진");
    controlMap.put("param3", text3);
    
    // 4. 도착역
    Label label4 = new Label(composite, SWT.NONE);
    label4.setText("도착역");
    Text text4 = new Text(composite, SWT.SINGLE);
    text4.setText("청량리");
    controlMap.put("param4", text4);
    
    // 5. 출발일
    Label label5 = new Label(composite, SWT.NONE);
    label5.setText("출발일");
    Text text5 = new Text(composite, SWT.SINGLE);
    text5.setText("2015.11.30.");
    controlMap.put("param5", text5);
    
    // 6. 시간
    Label label6 = new Label(composite, SWT.NONE);
    label6.setText("시간");
    Text text6 = new Text(composite, SWT.SINGLE);
    text6.setText("13");
    controlMap.put("param6", text6);
    
    // 7. 인원
    Label label7 = new Label(composite, SWT.NONE);
    label7.setText("인원");
    Text text7 = new Text(composite, SWT.SINGLE);
    text7.setText("1");
    controlMap.put("param7", text7);
  }
  
  public void setWebBrowser(Composite composite) {
    
    FormLayout layout = new FormLayout();
    composite.setLayout(layout);
    Browser browser = new Browser(composite, SWT.NONE);
    browser.setSize(500, 500);
    browser.setSize(1000, 600);
    browser.setUrl("http://www.letskorail.com/");
    
    browser.addProgressListener(new ProgressListener() {
      
      @Override
      public void completed(ProgressEvent event) {
        // TODO Auto-generated method stub
        System.out.println( "completed : " + event );
        
        System.out.println("Page loaded");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Execute JavaScript in the browser
        // browser.execute("alert(\"JavaScript, called from Java\"");
        // Use Javascript to set the browser width and height
        
        System.out.println(step);
        
        if (step==0) {
          browser.execute("document.getElementById('txtGoStart').value= '"+((Text)controlMap.get("param3")).getText()+"';"); // 출발역
          browser.execute("document.getElementById('txtGoEnd').value= '"+((Text)controlMap.get("param4")).getText()+"';"); // 도착역
          browser.execute("document.getElementById('selGoStartDay').value= '"+((Text)controlMap.get("param5")).getText()+"';"); // 출발일
          browser.execute("document.getElementById('time').value= '"+((Text)controlMap.get("param6")).getText()+"';"); // 시간
          browser.execute("document.getElementById('people_num').value= '"+((Text)controlMap.get("param7")).getText()+"';"); // 인원
          browser.execute("inqSchedule();");
          
        } else if (step==2) {
          
        }
        
        step++;
//        browser.execute("document.getElementById('map_canvas').style.height= "
//                        + (browser.getSize().y - 20) + ";");
      }
      
      @Override
      public void changed(ProgressEvent event) {
        // TODO Auto-generated method stub
      }
    }); 
  }
}
