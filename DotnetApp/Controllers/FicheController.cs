
namespace Controllers;

using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Models;
using Data;


public class FicheController : Controller {
    
    private readonly DatabaseContext _context;
    
    public FicheController(DatabaseContext context) {
        _context = context;
    }
    
    public async Task<IActionResult> Index() {
        return View(await _context.Fiches.ToListAsync());
    }
    
    public async Task<IActionResult> Edit(int? id) {
        if (id == null)
        {
            return NotFound();
        }
        var fiche = await _context.Fiches.FirstOrDefaultAsync(f => f.IdFiche == id);
        if(fiche == null)
        {
            return NotFound();
        }
        
        return View("FicheForm", fiche);
    }
    
    public IActionResult Create() {
        return View("FicheForm");
    }
    
    [HttpPost]
    public async Task<IActionResult> Update(Fiche fiche) {
        try
        {
            _context.Update(fiche);
            await _context.SaveChangesAsync();
        }
        catch(Exception e)
        {
            ViewData["ErrorMessage"] = e.InnerException.Message;
            return View("FicheForm", fiche);
        }
        return RedirectToAction(nameof(Index));
        ;
    }
    
    public async Task<IActionResult> Delete(int? id) {
        if (id == null)
        {
            return NotFound();
        }
        
        var fiche = await _context.Fiches.FindAsync(id);
        
        if (fiche == null)
        {
            return NotFound();
        }
        
        _context.Fiches.Remove(fiche);
        await _context.SaveChangesAsync();
        
        return RedirectToAction(nameof(Index));
    }
    
}
