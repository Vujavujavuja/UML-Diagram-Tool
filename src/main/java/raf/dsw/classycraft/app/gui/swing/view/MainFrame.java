package raf.dsw.classycraft.app.gui.swing.view;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.gui.swing.classyTree.ClassyTree;
import raf.dsw.classycraft.app.gui.swing.classyTree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.controller.ActionManager;
import raf.dsw.classycraft.app.gui.swing.view.optionBars.MyMenyBar;
import raf.dsw.classycraft.app.gui.swing.view.optionBars.MyToolBar;
import raf.dsw.classycraft.app.gui.swing.view.optionBars.UMLDiagramToolBar;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.ClassyTabView;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.PackageView;
import raf.dsw.classycraft.app.messageGenerator.Message;
import raf.dsw.classycraft.app.core.observer.ISubscriber;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MainFrame extends JFrame implements ISubscriber
{
    private static MainFrame instance;
    private ActionManager actionManager;
    private JMenuBar menu;
    private JToolBar toolBar;
    private JToolBar UMLToolBar;
    private JPanel eastPanel;
    private JSplitPane jSplitPane;
    private JSplitPane splitTabPane;
    private ClassyTabView classyTabView;
    private JPanel workingAreaPane;
    private ClassyTree classyTree;
    private List<ISubscriber> subscriberList;
    private PackageView packageView;
    private BasicSplitPaneUI createCustomSplitPaneUI() {
        return new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    public void paint(Graphics g) {
                        g.setColor(new Color(150, 150, 150));
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
                        super.paint(g);
                    }
                };
            }
        };
    }

    private MainFrame() {}

    private void initialise()
    {
        this.actionManager = new ActionManager();
        this.classyTree = new ClassyTreeImplementation();
        AppCore.getInstance().getMessageGenerator().addSubscriber(this);

        initialiseGUI();
    }

    private void initialiseGUI()
    {
        this.subscriberList = new ArrayList<>();

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        this.setSize(screenWidth / 2, screenHeight / 2 + 235); //+90 zbog zoomToFita i copy
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("ClassyCrafT");

        this.menu = new MyMenyBar();
        this.setJMenuBar(menu);

        this.toolBar = new MyToolBar();
        this.add(toolBar, BorderLayout.NORTH);

        this.UMLToolBar = new UMLDiagramToolBar();
        this.add(UMLToolBar, BorderLayout.EAST);

        this.eastPanel = new JPanel(new BorderLayout());
        this.eastPanel.add(UMLToolBar, BorderLayout.NORTH);
        this.add(eastPanel, BorderLayout.EAST);
        this.eastPanel.setVisible(true);

        this.jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT) {
        public void updateUI() {
            setUI(createCustomSplitPaneUI());
            revalidate();
        }
    };

        this.classyTabView = new ClassyTabView();
        this.packageView = new PackageView();
        JTree projectExplorer = classyTree.generateTree(AppCore.getInstance().getClassyRepository().getProjectExplorer(), classyTabView, packageView);
        this.workingAreaPane = new JPanel();
        JScrollPane scrollPane = new JScrollPane(projectExplorer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setMinimumSize(new Dimension(200, 150));

        this.splitTabPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT) {
            public void updateUI() {
                setUI(createCustomSplitPaneUI());
                revalidate();
            }
        };
        splitTabPane.setTopComponent(classyTabView);

        this.splitTabPane.setBottomComponent(packageView);
        this.splitTabPane.setResizeWeight(0.015);

        splitTabPane.setEnabled(false);
        splitTabPane.setOneTouchExpandable(false);

        if (splitTabPane.getUI() instanceof BasicSplitPaneUI) {
            BasicSplitPaneDivider divider = ((BasicSplitPaneUI) splitTabPane.getUI()).getDivider();
            divider.setBackground(Color.BLACK);
        }

        this.jSplitPane.setLeftComponent(scrollPane);
        this.jSplitPane.setRightComponent(splitTabPane);
        this.jSplitPane.setOneTouchExpandable(false);
        this.add(jSplitPane, BorderLayout.CENTER);
    }

    public static MainFrame getInstance()
    {
        if(instance == null)
        {
            instance = new MainFrame();
            instance.initialise();
        }
        return instance;
    }

    @Override
    public void update(Object notification, Object typeOfUpdate)
    {
        if(notification instanceof ClassyTabView) //Ako je doslo do neke promene, obavesteni smo i pozivamo repaint da bi se "crtez" osvezio i primenio promene
            this.repaint();

        Message message = (Message) notification;
        String messageType = ((Message)notification).getType();

        switch (messageType)
        {
            case "Information":
                JOptionPane.showMessageDialog(MainFrame.getInstance(), message.getContent(), message.getType(), JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Warning":
                JOptionPane.showMessageDialog(MainFrame.getInstance(), message.getContent(), message.getType(), JOptionPane.WARNING_MESSAGE);
                break;
            case "Error":
                JOptionPane.showMessageDialog(MainFrame.getInstance(), message.getContent(), message.getType(), JOptionPane.ERROR_MESSAGE);
                break;
            default:
                return;
        }
    }
}
