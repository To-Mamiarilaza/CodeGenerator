
namespace Controllers;

using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Models;
using Data;


public class PaysController : Controller {
    
    private readonly DatabaseContext _context;
    
    public PaysController(DatabaseContext context) {
        _context = context;
    }
    
    public async Task<IActionResult> Index() {
        return View(await _context.Payss.ToListAsync());
    }
    
    public async Task<IActionResult> Edit(int? id) {
        if (id == null)
        {
            return NotFound();
        }
        var pays = await _context.Payss.FirstOrDefaultAsync(p => p.IdPays == id);
        if(pays == null)
        {
            return NotFound();
        }
        
        return View("PaysForm", pays);
    }
    
    public IActionResult Create() {
        return View("PaysForm");
    }
    
    [HttpPost]
    public async Task<IActionResult> Update(Pays pays) {
        try
        {
            _context.Update(pays);
            await _context.SaveChangesAsync();
        }
        catch(Exception e)
        {
            ViewData["ErrorMessage"] = e.InnerException.Message;
            return View("PaysForm", pays);
        }
        return RedirectToAction(nameof(Index));
        ;
    }
    
    public async Task<IActionResult> Delete(int? id) {
        if (id == null)
        {
            return NotFound();
        }
        
        var pays = await _context.Payss.FindAsync(id);
        
        if (pays == null)
        {
            return NotFound();
        }
        
        _context.Payss.Remove(pays);
        await _context.SaveChangesAsync();
        
        return RedirectToAction(nameof(Index));
    }
    
}
