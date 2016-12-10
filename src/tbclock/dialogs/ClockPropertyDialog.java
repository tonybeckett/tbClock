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
package tbclock.dialogs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import tbclock.adapterbean.ClockAdapterBean;

import us.jaba.titaniumblocks.core.Scale;
import us.jaba.titaniumblocks.core.backdrop.BackdropCoreInfo;
import us.jaba.titaniumblocks.core.backdrop.types.Backdrop;
import us.jaba.titaniumblocks.core.frames.RoundFrame;
import us.jaba.titaniumblocks.core.frames.types.FramesCoreInfo;
import us.jaba.titaniumblocks.core.frontcover.FrontcoverCoreInfo;
import us.jaba.titaniumblocks.core.frontcover.types.Frontcover;
import us.jaba.titaniumblocks.core.pointers.AbstractPointer;
import us.jaba.titaniumblocks.core.pointers.PointerCoreInfo;
import us.jaba.titaniumblocks.core.posts.Post;
import us.jaba.titaniumblocks.core.posts.PostCoreInfo;
import us.jaba.titaniumblocks.core.tickmarks.marks.types.AbstractRadialTickmark;
import us.jaba.titaniumblocks.core.tickmarks.marks.types.clock.ClockCoreInfo;
import us.jaba.titaniumblocksswingui.property.adapterbean.BackdropAdapterBean;
import us.jaba.titaniumblocksswingui.property.adapterbean.FrameAdapterBean;
import us.jaba.titaniumblocksswingui.property.adapterbean.PointerAdapterBean;
import us.jaba.titaniumblocksswingui.property.adapterbean.TickmarkAdapterBean;
import us.jaba.titaniumblocksswingui.property.panels.ColorPanel;
import us.jaba.titaniumblocksswingui.property.panels.FontPanel;
import us.jaba.titaniumblocksswingui.property.panels.SliderPanel;
import us.jaba.titaniumblocksswingui.property.panels.TBPropertyPanel;
import us.jaba.titaniumblocksswingui.property.dialogs.BackdropPropertyDialog;
import us.jaba.titaniumblocksswingui.ClassMapper;
import us.jaba.titaniumblocksswingui.property.adapterbean.PostAdapterBean;
import us.jaba.titaniumblocksswingui.property.dialogs.FramePropertyDialog;
import us.jaba.titaniumblocksswingui.property.dialogs.PointerPropertyDialog;
import us.jaba.titaniumblocksswingui.property.dialogs.PostPropertyDialog;
import us.jaba.titaniumblocksswingui.property.dialogs.TickmarksPropertyDialog;

/**
 *
 * @author tbeckett
 */
public class ClockPropertyDialog extends javax.swing.JDialog
{

    ClassMapper<Backdrop> backdropMapper;
    ClassMapper<RoundFrame> frameMapper;
    ClassMapper<Frontcover> frontCoverMapper;
    ClassMapper<AbstractPointer> hourPointerMapper;
    private final Frame mainFrame;
    ClassMapper<AbstractPointer> minPointerMapper;
    private final ClassMapper postMapper;
    ClassMapper<AbstractPointer> secPointerMapper;
    ClassMapper<AbstractRadialTickmark> tickmarkMapper;

    /**
     * Creates new form SettingsDialog
     */
    public ClockPropertyDialog(java.awt.Frame parent, boolean modal, ClockAdapterBean clockBean)
    {

        super(parent, modal);
        this.mainFrame = parent;
        initComponents();
        setSize(400, 370);

        backdropMapper = new ClassMapper(BackdropCoreInfo.getInstanceOfRound(), clockBean.getBackdrop());
        this.PropertyPanel.add(new TBPropertyPanel("Backdrop", backdropMapper.getList(), clockBean.getBackdrop().getClass().getSimpleName())
        {
            @Override
            protected void selectAction(String text)
            {
                clockBean.setBackdrop(backdropMapper.getItem(text));
            }

            @Override
            protected void buttonAction()
            {
                BackdropPropertyDialog dialog = new BackdropPropertyDialog(mainFrame, true, new BackdropAdapterBean(clockBean.getBackdrop()));
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            }

        });

        frameMapper = new ClassMapper(FramesCoreInfo.getInstanceOfEachRound(), clockBean.getFrame());
        this.PropertyPanel.add(new TBPropertyPanel("Frame", frameMapper.getList(), clockBean.getFrame().getClass().getSimpleName())
        {
            @Override
            protected void selectAction(String text)
            {
                clockBean.setFrame(frameMapper.getItem(text));
            }

            @Override
            protected void buttonAction()
            {
                FramePropertyDialog dialog = new FramePropertyDialog(mainFrame, true, new FrameAdapterBean((RoundFrame) clockBean.getFrame()));
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            }

        });

        tickmarkMapper = new ClassMapper(ClockCoreInfo.getInstanceOfEach(), clockBean.getTickmarks());
        this.PropertyPanel.add(new TBPropertyPanel("Tickmarks", tickmarkMapper.getList(), clockBean.getTickmarks().getClass().getSimpleName())
        {
            @Override
            protected void selectAction(String text)
            {
                clockBean.setTickmarks(tickmarkMapper.getItem(text));
            }

            @Override
            protected void buttonAction()
            {
                TickmarksPropertyDialog dialog = new TickmarksPropertyDialog(mainFrame, true, new TickmarkAdapterBean((AbstractRadialTickmark) clockBean.getTickmarks()));
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            }
        });

        hourPointerMapper = new ClassMapper(PointerCoreInfo.getInstanceOfEach(), clockBean.getHoursPointer());
        this.PropertyPanel.add(new TBPropertyPanel("Hour Pointer", hourPointerMapper.getList(), clockBean.getHoursPointer().getClass().getSimpleName())
        {
            @Override
            protected void selectAction(String text)
            {
                clockBean.setHoursPointer(hourPointerMapper.getItem(text));
            }

            @Override
            protected void buttonAction()
            {
                PointerPropertyDialog dialog = new PointerPropertyDialog(mainFrame, true, new PointerAdapterBean(clockBean.getHoursPointer()));
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            }
        });

        minPointerMapper = new ClassMapper(PointerCoreInfo.getInstanceOfEach(), clockBean.getMinutesPointer());
        this.PropertyPanel.add(new TBPropertyPanel("Minute Pointer", minPointerMapper.getList(), clockBean.getMinutesPointer().getClass().getSimpleName())
        {
            @Override
            protected void selectAction(String text)
            {
                clockBean.setMinutesPointer(minPointerMapper.getItem(text));
            }

            @Override
            protected void buttonAction()
            {
                PointerPropertyDialog dialog = new PointerPropertyDialog(mainFrame, true, new PointerAdapterBean((AbstractPointer) clockBean.getMinutesPointer()));
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            }
        });

        secPointerMapper = new ClassMapper(PointerCoreInfo.getInstanceOfEach(), clockBean.getSecondsPointer());
        this.PropertyPanel.add(new TBPropertyPanel("Second Pointer", secPointerMapper.getList(), clockBean.getSecondsPointer().getClass().getSimpleName())
        {
            @Override
            protected void selectAction(String text)
            {
                clockBean.setSecondsPointer(secPointerMapper.getItem(text));
            }

            @Override
            protected void buttonAction()
            {
                PointerPropertyDialog dialog = new PointerPropertyDialog(mainFrame, true, new PointerAdapterBean((AbstractPointer) clockBean.getSecondsPointer()));
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            }
        });

        postMapper = new ClassMapper(PostCoreInfo.getInstanceOfEach(), clockBean.getCenterPost());
        this.PropertyPanel.add(new TBPropertyPanel("Center Post", postMapper.getList(), clockBean.getCenterPost().getClass().getSimpleName())
        {
            @Override
            protected void selectAction(String text)
            {
                clockBean.setCenterPost((Post) postMapper.getItem(text));
            }

            @Override
            protected void buttonAction()
            {
                PostPropertyDialog dialog = new PostPropertyDialog(mainFrame, true, new PostAdapterBean(clockBean.getCenterPost()));
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            }

        });

        frontCoverMapper = new ClassMapper(FrontcoverCoreInfo.getInstanceOfEach(), clockBean.getFrontCover());
        this.PropertyPanel.add(new TBPropertyPanel("Frontcover", frontCoverMapper.getList(), clockBean.getFrontCover().getClass().getSimpleName())
        {
            @Override
            protected void selectAction(String text)
            {
                clockBean.setFrontCover(frontCoverMapper.getItem(text));
            }
        });

        this.PropertyPanel.add(new FontPanel(mainFrame, "Title Font", clockBean.getTitleTextFont())
        {
            @Override
            public void fontChanged(Font font)
            {
                super.fontChanged(font);
                clockBean.setTitleTextFont(font);
            }
        }
        );

        this.PropertyPanel.add(new ColorPanel("Title Color", clockBean.getTitleTextColor())
        {
            @Override
            protected void update(Color color)
            {
                super.update(color);
                clockBean.setTitleTextColor(color);
            }

        }
        );

        this.PropertyPanel.add(new SliderPanel("Title Position", 100, 1, (int) (clockBean.getTitlePosScale().getValue() * 100.0))
        {
            @Override
            public void action(Integer value)
            {
                clockBean.setTitlePosScale(new Scale(value / 100.0));
            }

        }
        );

        this.addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosed(WindowEvent e)
            {

            }

            @Override
            public void windowClosing(WindowEvent e)
            {
                dialogClosing();
            }
        });
    }

    protected void dialogClosing()
    {
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

        PropertyPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        PropertyPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));
        PropertyPanel.setMinimumSize(new java.awt.Dimension(400, 300));
        PropertyPanel.setLayout(new java.awt.GridLayout(11, 0));
        getContentPane().add(PropertyPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[])
//    {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try
//        {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
//            {
//                if ("Nimbus".equals(info.getName()))
//                {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex)
//        {
//            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex)
//        {
//            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex)
//        {
//            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex)
//        {
//            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable()
//        {
//            public void run()
//            {
//                SettingsDialog dialog = new SettingsDialog(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter()
//                {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e)
//                    {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PropertyPanel;
    // End of variables declaration//GEN-END:variables
}
