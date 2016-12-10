/*
 * Copyright (c) 2015, Tony Beckett
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * The names of its contributors may not be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package tbclock;

import us.jaba.titaniumblocksswingui.dialogs.PropertyDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import tbclock.adapterbean.ClockAdapterBean;
import tbclock.adapterbean.SettingsAdapterBean;
import tbclock.dialogs.ClockPropertyDialog;
import tbclock.dialogs.SettingsDialog;
import us.jaba.titaniumblocks.core.Images;
import us.jaba.titaniumblocks.core.backdrop.types.Backdrop;
import us.jaba.titaniumblocks.core.color.ColorPalette;
import us.jaba.titaniumblocks.core.frames.RoundFrame;
import us.jaba.titaniumblocks.core.frames.types.round.NullRoundFrame;
import us.jaba.titaniumblocks.core.frontcover.types.Frontcover;
import us.jaba.titaniumblocks.core.posts.Post;
import us.jaba.titaniumblocks.core.utils.BeanIO;

import us.jaba.titaniumblocks.displays.TBClockComponent;
import us.jaba.titaniumblocks.displays.AbstractClockDial;
import us.jaba.titaniumblocks.displays.round.RoundClockDial;
import us.jaba.titaniumblocksswingui.Antimate;
import us.jaba.titaniumblocksswingui.panels.ClockPanel;
import us.jaba.titaniumblocksswingui.GlassPane;
import us.jaba.titaniumblocksswingui.SwingUtils;
import us.jaba.titaniumblocksswingui.property.panels.BooleanPanel;

/**
 *
 * @author tbeckett
 */
public class ClockFrame extends javax.swing.JFrame implements ActionListener
{

    class MySettingsAdapterBean extends SettingsAdapterBean implements Serializable
    {

        public MySettingsAdapterBean()
        {
        }

        @Override
        public void setMovable(boolean movable)
        {
            super.setMovable(movable);
            moveWindow = movable;
        }


        @Override
        public void setSize(int size)
        {
            super.setSize(size);
            frame.setSize(size, size);
        }

        @Override
        public void setAlwaysOnTop(boolean alwaysOnTop)
        {
            super.setAlwaysOnTop(alwaysOnTop);
            frame.setAlwaysOnTop(alwaysOnTop);
        }

        @Override
        public void setShowTimezone(boolean showTimezone)
        {
            super.setShowTimezone(showTimezone);

            if (isShowTimezone())
            {
                TimeZone tz = Calendar.getInstance().getTimeZone();
                String name = tz.getID();
                if (name.contains("/"))
                {
                    String[] sname = name.split("/");
                    name = sname[1];
                }
                name = name.replace("_", " ");
                panel.getTitleText().setValue(name);
            } else
            {
                panel.getTitleText().setValue(" ");
            }
        }

    };
    private static final String C_TEMPTBCLOCKXML = "C:/Temp/tbclock.xml";
    private static final String C_TEMP_SETTINGSXML = "C:/Temp/tbclockset.xml";
    private ClockFrame frame;
    private GlassPane glassPane;
    private BeanIO<AbstractClockDial> rcdio = new BeanIO();
    private BeanIO<SettingsAdapterBean> sabio = new BeanIO();
    ClockPanel panel;

    private JPopupMenu popup;
    private Point click;
    private boolean moveWindow = true;

    private MySettingsAdapterBean settings;

    PropertyDialog propertyDialog;

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }

    class PopupListener extends MouseAdapter
    {

        @Override
        public void mousePressed(MouseEvent e)
        {
            maybeShowPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e)
        {
            if (e.isPopupTrigger())
            {
                popup.show(e.getComponent(),
                        e.getX(), e.getY());
            }
        }
    }

    /**
     * Creates new form DisplaySingleDemo
     */
    public ClockFrame()
    {
        frame = this;
        init();
    }

    private void init()
    {
        setUndecorated(true);
        setLocationRelativeTo(null);

        initComponents();

        this.setIconImage(Images.titaniumblocks128);
        //Add listener to components that can bring up popup menus.
        MouseListener popupListener = new PopupListener();
        this.addMouseListener(popupListener);

        SwingUtils.enableTransparent(this);

        addComponentListener(new ComponentAdapter()
        {
            // Give the window an elliptical shape.
            // If the window is resized, the shape is recalculated here.
            @Override
            public void componentResized(ComponentEvent e)
            {
                setShape(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
            }
        });

        panel = new ClockPanel(new RoundClockDial());
        panel.setBackground(ColorPalette.WHITE);
        panel.setFrame(new NullRoundFrame());

//        panel.init(500, 500);
        add(panel, BorderLayout.CENTER);
        ;

        settings = new MySettingsAdapterBean();
        settings.setShowTimezone(false);
        settings.setAlwaysOnTop(false);
        settings.setMovable(moveWindow);
        settings.setStoreState(false);
        settings.setSize(500);

        Object obj = rcdio.loadFromFile(C_TEMPTBCLOCKXML);
        if (obj != null)
        {
            TBClockComponent acd = (TBClockComponent) obj;
            panel.setTBComponent(acd);
        }

    //    this.setSize(new Dimension(500, 500));
        obj = sabio.loadFromFile(C_TEMP_SETTINGSXML);
        if (obj != null)
        {
            SettingsAdapterBean s = (SettingsAdapterBean) obj;
            settings.setAlwaysOnTop(s.isAlwaysOnTop());
            settings.setMovable(s.isMovable());
            settings.setShowTimezone(s.isShowTimezone());
            settings.setSize(s.getSize());
            //settings.setStoreState(s.isStoreState());
        }

        popup = new JPopupMenu();

        JMenuItem menuItem = new JMenuItem("Settings");
        menuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SettingsDialog settingsDialog = new SettingsDialog(frame, true, settings)
                {
                    @Override
                    protected void dialogClosing()
                    {
                        if (settings.isStoreState())
                        {
                            try
                            {
                                SettingsAdapterBean s = new SettingsAdapterBean(settings);
                                sabio.storeToFile(s, C_TEMP_SETTINGSXML);
                            } catch (FileNotFoundException ex)
                            {
                                Logger.getLogger(ClockFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                };
                settingsDialog.addPanel(new BooleanPanel("Show time zone", settings.isShowTimezone())
                {
                    @Override
                    protected void update(Boolean b)
                    {
                        super.update(b);
                        settings.setShowTimezone(b);
                    }

                });

                settingsDialog.setTitle("Settings Dialog");
                settingsDialog.setLocationRelativeTo(panel);
                settingsDialog.setVisible(true);
            }
        });
        popup.add(menuItem);

        menuItem = new JMenuItem("Properties");
        menuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ClockAdapterBean bean = new ClockAdapterBean( (TBClockComponent) panel.getTBComponent());
                ClockPropertyDialog clockDialog = new ClockPropertyDialog(frame, true, bean)
                {
                    @Override
                    protected void dialogClosing()
                    {
                        if (settings.isStoreState())
                        {
                            try
                            {
                                rcdio.storeToFile((AbstractClockDial) panel.getTBComponent(), C_TEMPTBCLOCKXML);
                            } catch (FileNotFoundException ex)
                            {
                                Logger.getLogger(ClockFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                };

                clockDialog.setTitle("Clock Property Dialog");
                clockDialog.setLocationRelativeTo(panel);
                clockDialog.setVisible(true);

//                propertyDialog.setLocationRelativeTo(panel);
//                propertyDialog.setVisible(true);
            }

        });
        popup.add(menuItem);

        menuItem = new JMenuItem("About");
        menuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(panel, "<html>Titanium Blocks Clock        <br>https://github.com/tonybeckett/TitaniumBlocks</html>", "About - tbClock", JOptionPane.PLAIN_MESSAGE, new ImageIcon(Images.titaniumblocks32));
            }
        });
        popup.add(menuItem);

        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }

        });
        popup.add(menuItem);

        panel.add(popup);

        //       SystemTrayUtil.install(this);
        glassPane = new GlassPane(null, null, frame.getContentPane());
        frame.setGlassPane(glassPane);

        //       this.setEnabled(false);
        this.pack();
        this.pack();
        this.setSize(new Dimension(settings.getSize(), settings.getSize()));
        this.setTitle("tbClock");

        Antimate antimate = new Antimate(100.0, 0.1f)
        {
            @Override
            public void update(double d)
            {
                Calendar cal = Calendar.getInstance();
                Date t = new Date();

                panel.setValueAnimated(t.getHours(), t.getMinutes(), t.getSeconds());
            }
        };

        new Thread(antimate).start();
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                click = e.getPoint();
                getComponentAt(click);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                if (moveWindow)
                {
                    int x = getLocation().x;
                    int y = getLocation().y;

                    int xDiff = (x + e.getX()) - (x + click.x);
                    int yDiff = (y + e.getY()) - (y + click.y);

                    setLocation(x + xDiff, y + yDiff);
                }
            }
        });

    }

    public void setBackdrop(Backdrop painter)
    {
        panel.setBackdrop(painter);
    }

    public void setRoundFrame(RoundFrame linearFramePainter)
    {
        panel.setFrame(linearFramePainter);
    }

    public void setCenterPost(Post postPainter)
    {
        panel.setCenterPost(postPainter);
    }

    public void setFrontCover(Frontcover foregroundPainter)
    {
        panel.setFrontCover(foregroundPainter);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
