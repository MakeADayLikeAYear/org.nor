package org.nor.swt.tutorials;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class GridLayoutSWT {
  public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);

    // create a new GridLayout with two columns
    // of different size
    GridLayout layout = new GridLayout(2, false);

    // set the layout to the shell
    shell.setLayout(layout);

    // create a label and a button
    Label label = new Label(shell, SWT.NONE);
    label.setText("A label");
    Button button = new Button(shell, SWT.PUSH);
    button.setText("Press Me");

    // create a new label that will span two columns
    label = new Label(shell, SWT.BORDER);
    label.setText("This is a label");
    // create new layout data
    GridData data = new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1);
    label.setLayoutData(data);

    // create a new label which is used as a separator
    label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);

    // create new layout data
    data = new GridData(SWT.FILL, SWT.TOP, true, false);
    data.horizontalSpan = 2;
    label.setLayoutData(data);

    // create a right aligned button
    Button b = new Button(shell, SWT.PUSH);
    b.setText("New Button");

    data = new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1);
    b.setLayoutData(data);

    // create a spinner with min value 0 and max value 1000
    Spinner spinner = new Spinner(shell, SWT.READ_ONLY);
    spinner.setMinimum(0);
    spinner.setMaximum(1000);
    spinner.setSelection(500);
    spinner.setIncrement(1);
    spinner.setPageIncrement(100);
    GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
    gridData.widthHint = SWT.DEFAULT;
    gridData.heightHint = SWT.DEFAULT;
    gridData.horizontalSpan = 2;
    spinner.setLayoutData(gridData);

    Composite composite = new Composite(shell, SWT.BORDER);
    gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
    gridData.horizontalSpan = 2;
    composite.setLayoutData(gridData);
    composite.setLayout(new GridLayout(1, false));
    new GridLayoutSWT().Ch12WebBrowserComposite(composite);

    Text txtTest = new Text(composite, SWT.NONE);
    txtTest.setText("Testing");
    gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
    txtTest.setLayoutData(gridData);

    Text txtMoreTests = new Text(composite, SWT.NONE);
    txtMoreTests.setText("Another test");

    Group group = new Group(shell, SWT.NONE);
    group.setText("This is my group");
    gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
    gridData.horizontalSpan = 2;
    group.setLayoutData(gridData);
    group.setLayout(new RowLayout(SWT.VERTICAL));
    Text txtAnotherTest = new Text(group, SWT.NONE);
    txtAnotherTest.setText("Another test");

    shell.pack();
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    display.dispose();
  }

  public void Ch12WebBrowserComposite(Composite parent) {
    GridLayout layout = new GridLayout(2, true);
    parent.setLayout(layout);

    Browser browser = new Browser(parent, SWT.NONE);
    GridData layoutData = new GridData(GridData.FILL_BOTH);
    layoutData.horizontalSpan = 2;
    layoutData.verticalSpan = 2;
    browser.setLayoutData(layoutData);
    browser.setUrl("http://naver.com");

    final Text text = new Text(parent, SWT.SINGLE);
    layoutData = new GridData(GridData.FILL_HORIZONTAL);
    text.setLayoutData(layoutData);

    Button openButton = new Button(parent, SWT.PUSH);
    openButton.setText("Open");
    openButton.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
        browser.setUrl(text.getText());
      }

      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    Button backButton = new Button(parent, SWT.PUSH);
    backButton.setText("Back");
    backButton.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
        browser.back();
      }

      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    Button forwardButton = new Button(parent, SWT.PUSH);
    forwardButton.setText("Forward");
    forwardButton.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
        browser.forward();
      }

      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });
  }
}
