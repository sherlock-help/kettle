/*
 *
 *
 */

package org.pentaho.di.ui.repository.dialog;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogWriter;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectory;
import org.pentaho.di.ui.repository.dialog.Messages;
import org.pentaho.di.ui.core.dialog.ErrorDialog;

/**
 * Takes care of displaying a dialog that will handle the wait while we are exporting the complete
 * repository to XML...
 * 
 * @author Matt
 * @since 02-jun-2005
 */
public class RepositoryExportProgressDialog
{
    private Shell shell;
    private Repository rep;
    private RepositoryDirectory dir;
    private String filename;

    public RepositoryExportProgressDialog(Shell shell, Repository rep, RepositoryDirectory dir, String filename)
    {
        this.shell = shell;
        this.rep = rep;
        this.dir = dir;        
        this.filename = filename;
    }

    public boolean open()
    {
        boolean retval = true;

        IRunnableWithProgress op = new IRunnableWithProgress()
        {
            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
            {
                try
                {
                    rep.exportAllObjects(monitor, filename, dir);
                }
                catch (KettleException e)
                {
                    LogWriter.getInstance().logError(toString(), Const.getStackTracker(e));
                    throw new InvocationTargetException(e, Messages.getString("RepositoryExportDialog.Error.CreateUpdate", e.getMessage()));
                }
            }
        };

        try
        {
            ProgressMonitorDialog pmd = new ProgressMonitorDialog(shell);
            pmd.run(true, true, op);
        }
        catch (InvocationTargetException e)
        {
            LogWriter.getInstance().logError(RepositoryExportProgressDialog.class.toString(), "Error creating repository: " + e.toString());
            LogWriter.getInstance().logError(toString(), Const.getStackTracker(e));
            new ErrorDialog(shell, Messages.getString("RepositoryExportDialog.ErrorExport.Title"), Messages.getString("RepositoryExportDialog.ErrorExport.Message"), e);
            retval = false;
        }
        catch (InterruptedException e)
        {
            LogWriter.getInstance().logError(RepositoryExportProgressDialog.class.toString(), "Error creating repository: " + e.toString());
            LogWriter.getInstance().logError(toString(), Const.getStackTracker(e));
            new ErrorDialog(shell, Messages.getString("RepositoryExportDialog.ErrorExport.Title"), Messages.getString("RepositoryExportDialog.ErrorExport.Message"), e);
            retval = false;
        }

        return retval;
    }
}
