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
package tbclock.adapterbean;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import us.jaba.titaniumblocks.core.Scale;
import us.jaba.titaniumblocks.core.backdrop.types.Backdrop;
import us.jaba.titaniumblocks.core.frames.BasicFrame;
import us.jaba.titaniumblocks.core.frontcover.types.Frontcover;
import us.jaba.titaniumblocks.core.pointers.AbstractPointer;
import us.jaba.titaniumblocks.core.pointers.Pointer;
import us.jaba.titaniumblocks.core.posts.Post;
import us.jaba.titaniumblocks.core.text.Text;
import us.jaba.titaniumblocks.core.text.types.PolarText;
import us.jaba.titaniumblocks.core.tickmarks.marks.Tickmark;
import us.jaba.titaniumblocks.core.tickmarks.marks.types.AbstractRadialTickmark;

import us.jaba.titaniumblocks.displays.TBClockComponent;

/**
 *
 * @author tbeckett
 */
public class ClockAdapterBean implements TBClockComponent
{

    TBClockComponent component;
    int size;
    boolean alwaysOnTop;

    public ClockAdapterBean(TBClockComponent cd)
    {
        this.component = cd;
    }

    @Override
    public double getHoursValue()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setHoursValue(double firstPointerValue)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getMinutesValue()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMinutesValue(double secondPointerValue)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pointer getPointer()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPointer(Pointer pointer)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pointer getSecondsPointer()
    {
        return (AbstractPointer) component.getSecondsPointer();
    }

    @Override
    public void setSecondsPointer(Pointer pointer)
    {
        if (pointer != null)
        {
            ((AbstractPointer) pointer).copy((AbstractPointer) component.getSecondsPointer());
            component.setSecondsPointer(pointer);

        }
    }

    @Override
    public AbstractPointer getHoursPointer()
    {
        return (AbstractPointer) component.getHoursPointer();
    }

    @Override
    public void setHoursPointer(Pointer pointer)
    {
        if (pointer != null)
        {

            ((AbstractPointer) pointer).copy((AbstractPointer) component.getHoursPointer());
            component.setHoursPointer(pointer);
        }
    }

    @Override
    public Pointer getMinutesPointer()
    {
        return (Pointer) component.getMinutesPointer();
    }

    @Override
    public void setMinutesPointer(Pointer pointer)
    {
        if (pointer != null)
        {
            ((AbstractPointer) pointer).copy((AbstractPointer) component.getMinutesPointer());
            component.setMinutesPointer(pointer);
        }
    }

    @Override
    public double getSecondsValue()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSecondsValue(double thirdPointerValue)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Dimension getSize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSize(Dimension d)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tickmark getTickmarks()
    {
        return component.getTickmarks();
    }

    @Override
    public void setTickmarks(Tickmark tm)
    {
        if (tm != null)
        {
            try
            {
                AbstractRadialTickmark ntm = (AbstractRadialTickmark) tm.getClass().newInstance();
                ntm.copy((AbstractRadialTickmark) component.getTickmarks());
                component.setTickmarks(ntm);
            } catch (InstantiationException | IllegalAccessException ex)
            {

            }
        }
    }

    @Override
    public Backdrop getBackdrop()
    {
        return component.getBackdrop();
    }

    @Override
    public void setBackdrop(Backdrop backdrop)
    {
        if (backdrop != null)
        {
            component.setBackdrop(backdrop);
        }
    }

    @Override
    public BasicFrame getFrame()
    {
        return component.getFrame();
    }

    @Override
    public void setFrame(BasicFrame frame)
    {
        if (frame != null)
        {
            component.setFrame(frame);
        }
    }

    @Override
    public void setCenterPost(Post post)
    {
        if (post != null)
        {
            component.setCenterPost(post);
        }
    }

    @Override
    public void setFrontCover(Frontcover frontcover)
    {
        if (frontcover != null)
        {
            component.setFrontCover(frontcover);
        }
    }

    @Override
    public Post getCenterPost()
    {
        return component.getCenterPost();
    }

    @Override
    public Frontcover getFrontCover()
    {
        return component.getFrontCover();
    }

   

    @Override
    public Text getText1()
    {
        return component.getText1();
    }

    @Override
    public void setText1(Text titleText)
    {
       
    }

    public Font getTitleTextFont()
    {
        return component.getText1().getFont();
    }

    public void setTitleTextFont(Font font)
    {
        component.getText1().setFont(font);
    }

    public Color getTitleTextColor()
    {
        return component.getText1().getColor();
    }

    public void setTitleTextColor(Color color)
    {
        component.getText1().setColor(color);
    }

    public Scale getTitlePosScale()
    {
        return ((PolarText)component.getText1()).getRadiusAdjust();
    }

    public void setTitlePosScale(Scale scale)
    {
        ((PolarText)component.getText1()).setRadiusAdjust(scale);
    }

    @Override
    public void paint(Graphics2D graphics2D, Dimension dimension)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setChanged()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
