
namespace Controllers;

using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Models;
using Data;


public class TutorialsController : Controller {
    
    private readonly DatabaseContext _context;
    
    public TutorialsController(DatabaseContext context) {
        _context = context;
    }
    
    public async Task<IActionResult> Index() {
        return View(await _context.Tutorialss.ToListAsync());
    }
    
    public async Task<IActionResult> Edit(int? id) {
        if (id == null)
        {
            return NotFound();
        }
        var tutorials = await _context.Tutorialss.FirstOrDefaultAsync(t => t.Id == id);
        if(tutorials == null)
        {
            return NotFound();
        }
        
        return View("TutorialsForm", tutorials);
    }
    
    public IActionResult Create() {
        return View("TutorialsForm");
    }
    
    [HttpPost]
    public async Task<IActionResult> Update(Tutorials tutorials) {
        try
        {
            _context.Update(tutorials);
            await _context.SaveChangesAsync();
        }
        catch(Exception e)
        {
            ViewData["ErrorMessage"] = e.InnerException.Message;
            return View("TutorialsForm", tutorials);
        }
        return RedirectToAction(nameof(Index));
        ;
    }
    
    public async Task<IActionResult> Delete(int? id) {
        if (id == null)
        {
            return NotFound();
        }
        
        var tutorials = await _context.Tutorialss.FindAsync(id);
        
        if (tutorials == null)
        {
            return NotFound();
        }
        
        _context.Tutorialss.Remove(tutorials);
        await _context.SaveChangesAsync();
        
        return RedirectToAction(nameof(Index));
    }
    
}
